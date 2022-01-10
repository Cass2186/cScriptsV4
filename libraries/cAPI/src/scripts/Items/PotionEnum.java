package scripts.Items;

import org.tribot.api2007.Skills.SKILLS;


public enum PotionEnum {

    COMBAT {
        @Override
        public SKILLS getCorrespondingSkill() {
            return SKILLS.STRENGTH;
        }
    },

    SUPER_COMBAT {
        @Override
        public SKILLS getCorrespondingSkill() {
            return SKILLS.STRENGTH;
        }
    },


    RANGING {
        @Override
        public SKILLS getCorrespondingSkill() {
            return SKILLS.RANGED;
        }
    },

    STRENGTH {
        @Override
        public SKILLS getCorrespondingSkill() {
            return SKILLS.STRENGTH;
        }
    },

    ATTACK {
        @Override
        public SKILLS getCorrespondingSkill() {
            return SKILLS.ATTACK;
        }
    },

    DEFENCE {
        @Override
        public SKILLS getCorrespondingSkill() {
            return SKILLS.DEFENCE;
        }
    },

    MAGIC {
        @Override
        public SKILLS getCorrespondingSkill() {
            return SKILLS.MAGIC;
        }
    },

    BASTION {
        @Override
        public SKILLS getCorrespondingSkill() {
            return SKILLS.RANGED;
        }
    },

    BATTLEMAGE {
        @Override
        public SKILLS getCorrespondingSkill() {
            return SKILLS.MAGIC;
        }
    };

    public abstract SKILLS getCorrespondingSkill();

}