package scripts.RcApi;

import org.apache.commons.lang3.StringUtils;

public enum AbyssTask {

    COSMIC,
    BLOOD;

    @Override
    public String toString(){
        return StringUtils.capitalize(this.toString().toLowerCase());
    }


}
