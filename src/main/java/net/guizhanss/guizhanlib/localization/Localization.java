package net.guizhanss.guizhanlib.localization;

import org.apache.commons.lang.Validate;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

/**
 * Localization service.
 * Should be initialized after loading config file and before registering items.
 *
 * Localization service will create a folder "lang" (by default) under plugin's data folder.
 *
 * @author ybw0014
 */
public class Localization {

    private boolean initialized = false;

    private final JavaPlugin plugin;
    private final String langFolderName;
    private final File langFolder;
    private final List<String> languages = new LinkedList<>();
    private final Map<String, Language> langMap = new LinkedHashMap<>();

    /**
     * Constructor
     *
     * @param plugin The {@link JavaPlugin} instance
     */
    @ParametersAreNonnullByDefault
    public Localization(JavaPlugin plugin) {
        this(plugin, "lang");
    }

    /**
     * Constructor
     *
     * @param plugin The {@link JavaPlugin} instance
     * @param folderName The name of the folder that holds all language files
     */
    @ParametersAreNonnullByDefault
    public Localization(JavaPlugin plugin, String folderName) {
        Validate.notNull(plugin, "The plugin instance should not be null");
        Validate.notNull(folderName, "The folder name should not be null");

        this.plugin = plugin;

        // Check data folder
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }

        // Check language folder
        this.langFolderName = folderName;
        langFolder = new File(plugin.getDataFolder(), "/" + folderName);
        if (!langFolder.exists()) {
            langFolder.mkdir();
        }

        this.initialized = true;
    }

    /**
     * Constructor
     *
     * @param plugin The {@link JavaPlugin} instance
     * @param folderName The name of the folder that holds all language files
     * @param langFile The name of language file (without file extension .yml)
     */
    @ParametersAreNonnullByDefault
    public Localization(JavaPlugin plugin, String folderName, String langFile) {
        this(plugin, folderName);
        addLanguage(langFile);
    }

    /**
     * Load a language file to configuration
     *
     * @param langFilename the filename of language file without extension .yml
     *
     * @throws IllegalStateException when language file does not exist
     */
    public void addLanguage(@Nonnull String langFilename) {
        Validate.notNull(langFilename, "The language file name should not be null");

        File langFile = new File(langFolder, langFilename + ".yml");
        String resourcePath = langFolderName + "/" + langFilename + ".yml";
        if (!langFile.exists()) {
            try {
                plugin.saveResource(resourcePath, false);
            } catch (IllegalArgumentException ex) {
                plugin.getLogger().log(Level.SEVERE, "The language file {0} does not exist in jar file!", resourcePath);
                return;
            }
        }

        languages.add(langFilename);

        InputStreamReader defaultReader = new InputStreamReader(plugin.getResource(resourcePath), StandardCharsets.UTF_8);
        FileConfiguration defaultConfig = YamlConfiguration.loadConfiguration(defaultReader);
        langMap.put(langFilename, new Language(langFilename, langFile, defaultConfig));
    }

    /**
     * Get localized {@link String} from path
     *
     * @param path the localization path
     *
     * @return Localized {@link String}, empty if string is not found
     */
    public @Nonnull String getString(@Nonnull String path) {
        Validate.notNull(path, "path cannot be null");
        if (!initialized) {
            throw new IllegalStateException("Localization service is not initialized");
        }

        for (String lang : languages) {
            String localization = langMap.get(lang).getLang().getString(path);
            if (localization != null) {
                return localization;
            }
        }
        return "";
    }

    /**
     * Get localized {@link String} {@link List} from path
     *
     * @param path the localization path
     *
     * @return Localized {@link String} {@link List}
     */
    public @Nonnull List<String> getStringList(@Nonnull String path) {
        Validate.notNull(path, "path cannot be null");
        if (!initialized) {
            throw new IllegalStateException("Localization service is not initialized");
        }

        for (String lang : languages) {
            List<String> localization = langMap.get(lang).getLang().getStringList(path);
            if (!localization.isEmpty()) {
                return localization;
            }
        }
        return new ArrayList<>();
    }

    /**
     * Get localized {@link String} array from path
     *
     * @param path the localization path
     *
     * @return Localized {@link String} array
     */
    public @Nonnull String[] getStringArray(@Nonnull String path) {
        return getStringList(path).toArray(new String[0]);
    }
}
