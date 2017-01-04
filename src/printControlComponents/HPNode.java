package printControlComponents;

/**
 * Created by ycqfeng on 2017/1/4.
 */
public class HPNode {
    private IF_HPrint instance;

    private boolean isPrintErrorInformation;
    private boolean isPrintLogicInformation;
    private boolean isPrintDebugInformation;
    private boolean isPrintResultInformation;

    public HPNode(IF_HPrint instance){
        this.instance = instance;
        this.isPrintDebugInformation = true;
        this.isPrintErrorInformation = true;
        this.isPrintLogicInformation = true;
        this.isPrintResultInformation = true;
    }

    public IF_HPrint getInstance() {
        return instance;
    }

    public void setALL(boolean bool){
        isPrintResultInformation = bool;
        isPrintLogicInformation = bool;
        isPrintErrorInformation = bool;
        isPrintDebugInformation = bool;
    }

    public boolean isPrintErrorInformation() {
        return isPrintErrorInformation;
    }

    public void setPrintErrorInformation(boolean printErrorInformation) {
        isPrintErrorInformation = printErrorInformation;
    }

    public boolean isPrintLogicInformation() {
        return isPrintLogicInformation;
    }

    public void setPrintLogicInformation(boolean printLogicInformation) {
        isPrintLogicInformation = printLogicInformation;
    }

    public boolean isPrintDebugInformation() {
        return isPrintDebugInformation;
    }

    public void setPrintDebugInformation(boolean printDebugInformation) {
        isPrintDebugInformation = printDebugInformation;
    }

    public boolean isPrintResultInformation() {
        return isPrintResultInformation;
    }

    public void setPrintResultInformation(boolean printResultInformation) {
        isPrintResultInformation = printResultInformation;
    }
}
