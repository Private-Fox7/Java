class Conversion {
    public static void main(String[] args) {

        int intnum = 30;
        String s1 = String.valueOf(intnum);
        System.out.println(s1);
        String s2 = new String("STRING DATA");
        String s4 = new String("JAVA");
        String s3 = String.valueOf(s2);
        System.out.println(s3);
        System.out.println(s3 + s4);
        Conversion obj = null;
        String strNull = String.valueOf(obj);
        System.out.println(strNull);
    }
}