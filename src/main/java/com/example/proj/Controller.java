package com.example.proj;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Controller {

    @FXML
    private TextArea GenKEY;

    @FXML
    private TextArea KSAText;

    @FXML
    private TextArea binnary;

    @FXML
    private TextArea cipherText;

    @FXML
    private TextField keyText;

    @FXML
    private TextArea palinText;

    int [] s = new int[256];
    int [] t = new int[256];
    @FXML
    void startEncr(ActionEvent event) {
        clr();

        String plainText = palinText.getText();
        plainText = txtToBinnary(plainText);
        binnary.appendText(plainText);
        if (!keyText.getText().isEmpty())
            KSA(keyText.getText());
        else{
            keyText.setText("Enter KEY PLEASE");
            return;
        }

        String genKey = PRGA(plainText.length());
        GenKEY.appendText(genKey);
        genKey = txtToBinnary(genKey);
        int [] arrplain = strToInt(plainText);
        int [] arrkey = strToInt(genKey);
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < arrplain.length; i++) {
            int xor = arrplain[i] ^ arrkey[i];
            out.append(xor);
        }
        String output = out.toString();
        output = binnaryToTxt(output);
        cipherText.appendText(output);

    }

    public void KSA(String key){
        int [] arrkey = strToInt(key);
        int j=0;
        for (int i = 0; i < 256; i++) {
            s[i]=i;
            t[i]=arrkey[i%key.length()];
        }
        for (int i = 0; i < 256; i++) {
            j = (j+s[i]+t[i])%256;
            swap(i,j);
        }
        print();

    }
    public   String PRGA(int lenght){
        int i=0,j=0,key;
        StringBuilder Key = new StringBuilder();
        for (int k = 0; k < lenght; k++) {
            i = (i+1)%256;
            j = (j+s[i])%256;
            swap(i,j);
            key = s[(s[i]+s[j])%256];
            Key.append(key);

        }

        return Key.toString();

    }

    private   void swap(int i, int j) {
        int temp = s[i];
        s[i]=s[j];
        s[j]=temp;
    }

    public   int[] strToInt(String input){
        int [] toInt = new int[input.length()];
        for (int i = 0; i < input.length(); i++) {
            toInt[i] = (int) input.charAt(i);
        }
        return  toInt;
    }
    public   void print(){
        String S = new String();
        for (int i = 0; i < 256; i++) {
           S = S +"s["+i+"]="+s[i]+"\n";
        }
        KSAText.appendText(S);

    }
    public   String txtToBinnary(String input){
        StringBuilder st = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            int charACII = (int)input.charAt(i);
            for (int j = 0; j < (8 - Integer.toBinaryString(charACII).length()); j++) {
                st.append("0");
            }
            st.append(Integer.toBinaryString(charACII));

        }
        return st.toString();
    }
    public   String binnaryToTxt(String input){
        StringBuilder plain = new StringBuilder();
        String c;
        for (int i = 0; i < input.length(); i+=8) {
            c=input.substring(i,i+8);
            char ch = (char) Integer.parseInt(c,2);
            plain.append(ch);
        }
        return plain.toString();
    }
    void clr(){
        binnary.clear();
        GenKEY.clear();
        cipherText.clear();
        KSAText.clear();
    }
}



