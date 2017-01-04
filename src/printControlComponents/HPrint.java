package printControlComponents;

import han_simulatorComponents.Simulator;

/**
 * Created by ycqfeng on 2017/1/4.
 */
public class HPrint {
    public static HPrint hPrint;

    private HPNode[] nodes;
    private Simulator simulator;

    public static String getCurrTime(){
        String str = "";
        str += HPrint.hPrint.simulator.getCurTime()+"s, ";
        return str;
    }
    public static void printlnt(String str){
        str = getCurrTime() + str;
        System.out.println(str);
    }

    private static void error_unRegister(IF_HPrint instance){
        String error = "实例"+instance.getClass().getName()+"未注册，无法打印输出。";
        System.out.println(error);
    }

    public static boolean printlnDebugInfot(IF_HPrint instance, String str){
        HPNode hpNode = getHPNode(instance);
        if (hpNode == null){
            error_unRegister(instance);
            return false;
        }
        if (hpNode.isPrintDebugInformation()){
            str = getCurrTime() + str + "--(Debug Info)";
            System.out.println(str);
            return true;
        }
        else{
            return false;
        }
    }

    public static boolean printlnDebugInfo(IF_HPrint instance, String str){
        HPNode hpNode = getHPNode(instance);
        if (hpNode == null){
            error_unRegister(instance);
            return false;
        }
        if (hpNode.isPrintDebugInformation()){
            str = str + "--(Debug Info)";
            System.out.println(str);
            return true;
        }
        else{
            return false;
        }
    }
    public static boolean printlnResultInfot(IF_HPrint instance, String str){
        HPNode hpNode = getHPNode(instance);
        if (hpNode == null){
            error_unRegister(instance);
            return false;
        }
        if (hpNode.isPrintResultInformation()){
            str = getCurrTime() + str + "--(Result Info)";
            System.out.println(str);
            return true;
        }
        else{
            return false;
        }
    }

    public static boolean printlnResultInfo(IF_HPrint instance, String str){
        HPNode hpNode = getHPNode(instance);
        if (hpNode == null){
            error_unRegister(instance);
            return false;
        }
        if (hpNode.isPrintResultInformation()){
            str = str + "--(Result Info)";
            System.out.println(str);
            return true;
        }
        else{
            return false;
        }
    }

    public static boolean printlnLogicInfot(IF_HPrint instance, String str){
        HPNode hpNode = getHPNode(instance);
        if (hpNode == null){
            error_unRegister(instance);
            return false;
        }
        if (hpNode.isPrintLogicInformation()){
            str = getCurrTime() + str + "--(Logic Info)";
            System.out.println(str);
            return true;
        }
        else{
            return false;
        }
    }

    public static boolean printlnLogicInfo(IF_HPrint instance, String str){
        HPNode hpNode = getHPNode(instance);
        if (hpNode == null){
            error_unRegister(instance);
            return false;
        }
        if (hpNode.isPrintLogicInformation()){
            str = str + "--(Logic Info)";
            System.out.println(str);
            return true;
        }
        else{
            return false;
        }
    }

    public static boolean printlnErrorInfot(IF_HPrint instance, String str){
        HPNode hpNode = getHPNode(instance);
        if (hpNode == null){
            error_unRegister(instance);
            return false;
        }
        if (hpNode.isPrintErrorInformation()){
            str = getCurrTime() + str + "--(Error Info)";
            System.out.println(str);
            return true;
        }
        else{
            return false;
        }
    }

    public static boolean printlnErrorInfo(IF_HPrint instance, String str){
        HPNode hpNode = getHPNode(instance);
        if (hpNode == null){
            error_unRegister(instance);
            return false;
        }
        if (hpNode.isPrintErrorInformation()){
            str = str + "--(Error Info)";
            System.out.println(str);
            return true;
        }
        else{
            return false;
        }
    }

    public void setSimulator(Simulator simulator){
        this.simulator = simulator;
    }

    public static HPNode getHPNode(IF_HPrint instance){
        for (int i = 0 ; i < hPrint.nodes.length ; i++){
            if (hPrint.nodes[i].getInstance() == instance){
                return hPrint.nodes[i];
            }
        }
        return null;
    }

    public static void register(IF_HPrint instance){
        if (hPrint == null){
            hPrint = new HPrint();
            hPrint.nodes = new HPNode[1];
            hPrint.nodes[0] = new HPNode(instance);
            return;
        }
        else{
            if (getHPNode(instance) != null){
                return;
            }
            HPNode[] tnodes = new HPNode[hPrint.nodes.length+1];
            System.arraycopy(hPrint.nodes, 0, tnodes, 0, hPrint.nodes.length);
            tnodes[hPrint.nodes.length] = new HPNode(instance);
            hPrint.nodes = tnodes;
            return;
        }
    }
}
