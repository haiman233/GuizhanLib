package net.guizhanss.guizhanlib.minecraft.helper.entity;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import net.guizhanss.guizhanlib.utils.StringUtil;
import org.apache.commons.lang.Validate;
import org.bukkit.entity.Panda;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * 熊猫({@link Panda})
 *
 * @author ybw0014
 */
@UtilityClass
public class PandaHelper {
    /**
     * 所有熊猫基因
     */
    public enum Gene {
        /**
         * 好斗
         */
        AGGRESSIVE(Panda.Gene.AGGRESSIVE, "Aggressive", "好斗"),
        /**
         * 棕色
         */
        BROWN(Panda.Gene.BROWN, "Brown", "棕色"),
        /**
         * 懒惰
         */
        LAZY(Panda.Gene.LAZY, "Lazy", "懒惰"),
        /**
         * 普通
         */
        NORMAL(Panda.Gene.NORMAL, "Normal", "普通"),
        /**
         * 顽皮
         */
        PLAYFUL(Panda.Gene.PLAYFUL, "Playful", "顽皮"),
        /**
         * 虚弱
         */
        WEAK(Panda.Gene.WEAK, "Weak", "虚弱"),
        /**
         * 发愁
         */
        WORRIED(Panda.Gene.WORRIED, "Worried", "发愁");

        private final @Getter Panda.Gene gene;
        private final @Getter String english;
        private final @Getter String chinese;

        @ParametersAreNonnullByDefault
        Gene(Panda.Gene gene, String english, String chinese) {
            this.gene = gene;
            this.english = english;
            this.chinese = chinese;
        }

        @Override
        public String toString() {
            return this.getChinese();
        }

        /**
         * 根据熊猫基因返回对应的枚举
         *
         * @param pandaGene {@link Panda.Gene} 熊猫基因
         *
         * @return 对应的枚举
         */
        public static @Nonnull Gene fromGene(@Nonnull Panda.Gene pandaGene) {
            Validate.notNull(pandaGene, "熊猫基因不能为空");

            for (Gene gene : Gene.values()) {
                if (gene.getGene() == pandaGene) {
                    return gene;
                }
            }
            throw new IllegalArgumentException("无效的熊猫基因");
        }

        /**
         * 根据英文返回对应的枚举
         * @param english {@link String} 提供的英文
         * @return 对应的枚举
         */
        public static @Nullable Gene fromEnglish(@Nonnull String english) {
            Validate.notNull(english, "英文不能为空");

            String humanized = StringUtil.humanize(english);
            for (Gene gene : Gene.values()) {
                if (gene.getEnglish().equals(humanized)) {
                    return gene;
                }
            }
            return null;
        }
    }

    /**
     * 获取熊猫基因({@link Panda.Gene})的中文
     *
     * @param gene {@link Panda.Gene} 熊猫基因
     *
     * @return 熊猫基因的中文
     */
    public static @Nonnull String getGene(@Nonnull Panda.Gene gene) {
        return Gene.fromGene(gene).getChinese();
    }

    /**
     * 获取熊猫基因({@link Panda.Gene})的中文
     *
     * @param gene {@link String} 熊猫基因
     *
     * @return 熊猫基因的中文
     */
    public static @Nonnull String getGene(@Nonnull String gene) {
        Validate.notNull(gene, "熊猫基因不能为空");

        try {
            Panda.Gene pandaGene = Panda.Gene.valueOf(gene);
            return Gene.fromGene(pandaGene).getChinese();
        } catch (IllegalArgumentException ex) {
            return StringUtil.humanize(gene);
        }
    }
}
