package scripts.API;

public enum SkillNum {

    ONE(1, "skill name");

    int num;
    String skillName;

    SkillNum(int num, String skillName){
        this.num = num;
        this.skillName = skillName;
    }

}
