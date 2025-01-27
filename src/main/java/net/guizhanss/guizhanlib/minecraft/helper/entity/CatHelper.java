package net.guizhanss.guizhanlib.minecraft.helper.entity;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import net.guizhanss.guizhanlib.utils.StringUtil;
import org.apache.commons.lang.Validate;
import org.bukkit.entity.Cat;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * 猫({@link Cat})
 *
 * @author ybw0014
 */
@UtilityClass
public class CatHelper {
    /**
     * 所有猫的类型
     */
    public enum Type {
        /**
         * 黑猫
         */
        ALL_BLACK(Cat.Type.ALL_BLACK, "Black", "黑猫"),
        /**
         * 西服猫
         */
        BLACK(Cat.Type.BLACK, "Tuxedo", "西服猫"),
        /**
         * 英国短毛猫
         */
        BRITISH_SHORTHAIR(Cat.Type.BRITISH_SHORTHAIR, "British Shorthair", "英国短毛猫"),
        /**
         * 花猫
         */
        CALICO(Cat.Type.CALICO, "Calico", "花猫"),
        /**
         * Jellie
         */
        JELLIE(Cat.Type.JELLIE, "Jellie", "Jellie"),
        /**
         * 波斯猫
         */
        PERSIAN(Cat.Type.PERSIAN, "Persian", "波斯猫"),
        /**
         * 布偶猫
         */
        RAGDOLL(Cat.Type.RAGDOLL, "Ragdoll", "布偶猫"),
        /**
         * 红虎斑猫
         */
        RED(Cat.Type.RED, "Red", "红虎斑猫"),
        /**
         * 暹罗猫
         */
        SIAMESE(Cat.Type.SIAMESE, "Siamese", "暹罗猫"),
        /**
         * 虎斑猫
         */
        TABBY(Cat.Type.TABBY, "Tabby", "虎斑猫"),
        /**
         * 白猫
         */
        WHITE(Cat.Type.WHITE, "Calico", "白猫");

        private final @Getter Cat.Type type;
        private final @Getter String english;
        private final @Getter String chinese;

        @ParametersAreNonnullByDefault
        Type(Cat.Type type, String english, String chinese) {
            this.type = type;
            this.english = english;
            this.chinese = chinese;
        }

        @Override
        public String toString() {
            return this.getChinese();
        }

        /**
         * 根据猫的类型返回对应的枚举
         *
         * @param catType {@link Cat.Type} 猫的类型
         *
         * @return 对应的枚举
         */
        public static @Nonnull Type fromType(@Nonnull Cat.Type catType) {
            Validate.notNull(catType, "猫类型不能为空");

            for (Type type : Type.values()) {
                if (type.getType() == catType) {
                    return type;
                }
            }
            throw new IllegalArgumentException("无效的猫类型");
        }

        /**
         * 根据英文返回对应的枚举
         *
         * @param english {@link String} 提供的英文
         *
         * @return 对应的枚举
         */
        public static @Nullable Type fromEnglish(@Nonnull String english) {
            Validate.notNull(english, "英文不能为空");

            String humanized = StringUtil.humanize(english);
            for (Type type : Type.values()) {
                if (type.getEnglish().equals(humanized)) {
                    return type;
                }
            }
            return null;
        }
    }

    /**
     * 获取猫的类型({@link Cat.Type})的中文
     *
     * @param type {@link Cat.Type} 猫的类型
     *
     * @return 猫的类型的中文
     */
    public static @Nonnull String getType(@Nonnull Cat.Type type) {
        return Type.fromType(type).getChinese();
    }

    /**
     * 获取猫的类型({@link Cat.Type})的中文
     *
     * @param type {@link String} 猫的类型
     *
     * @return 猫的类型的中文
     */
    public static @Nonnull String getType(@Nonnull String type) {
        Validate.notNull(type, "猫类型不能为空");

        try {
            Cat.Type catType = Cat.Type.valueOf(type);
            return Type.fromType(catType).getChinese();
        } catch (IllegalArgumentException ex) {
            return StringUtil.humanize(type);
        }
    }
}
