package util;

public class AlphabetCheck {
    public static boolean isRussian(String text){
        String ruAlphabet = "АаБбВвГгДдЕеЁёЖжЗзИиЙйКкЛлМмНнОоПпРрСсТтУуФфХхЦцЧчШшЩщЪъЫыЬьЭэЮюЯя ,.!?;:";
        for (char c: text.toCharArray()) {
            if(!ruAlphabet.contains(String.valueOf(c))){
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                return false;
            }
        }
        return true;
    }
}
