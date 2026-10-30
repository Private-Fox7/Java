import java.io.IOException;

public class read {
    public static void main(String[] args) throws IOException {
        char charac = (char) System.in.read();
        System.out.println("Character entered is :" + charac);
    }
}

// Use Reader when working with txt,csv,java,xml
// Use InputStream when working with images , pdf , audio,videos,Zip files