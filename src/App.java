
import com.somnath.*;

public class App {
    public static void main(String[] args) throws Exception {
        String url = "https://www.flipkart.com/triggr-ultrabuds-n1-neo-enc-40hr-playback-13mm-drivers-rich-bass-fast-charging-bluetooth/p/itm39cb3c540b4c6?pid=ACCH2WWTUZNGGDVC&lid=LSTACCH2WWTUZNGGDVCPEWLUQ&marketplace=FLIPKART&store=0pm%2Ffcn&srno=b_1_2&otracker=browse&fm=organic&iid=en_e4Sokmme-_i3H9O3_B3kRdfrUCwy9l659Sdrf8MwZKb3tYIAcGggrOsgWlqI5japvEtNvvRklpr0gJU2UuC1ufUFjCTyOHoHZs-Z5_PS_w0%3D&ppt=browse&ppn=browse&ssid=c5l91ppb0g0000001732214749900";
        WebCrawler crawler = new WebCrawler(url);
        System.out.println(crawler.getCurrentPrice());
        System.out.println(crawler.getFullPrice());
        
    }
}
