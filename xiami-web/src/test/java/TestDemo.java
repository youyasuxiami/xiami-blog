import com.xiami.utils.AddressUtil;

/**
 * Description：
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­12­31 12:47
 */
public class TestDemo {

    public static void main(String[] args) {
        String ip="121.32.78.251";
        String cityInfo = AddressUtil.getAddress(ip);
        System.out.println(cityInfo);

    }
}
