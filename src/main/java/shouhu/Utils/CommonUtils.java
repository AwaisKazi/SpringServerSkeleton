package shouhu.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by liushanchen on 16/6/5.
 */
public class CommonUtils {
    /**
     * 获取当前时间戳
     * @return
     */
    public static final String getTimeStamp() {
        return new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
    }
}
