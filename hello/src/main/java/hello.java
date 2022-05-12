import org.junit.Test;

import java.time.*;
import java.util.Locale;
import java.util.Scanner;
import java.util.TreeSet;

/**
 * @author wanjun
 * @create 2022-04-14 16:04
 */
public class hello {
    public String hello(String input){
        //在线修改
        // 本地上传
        return "hi1111"+input+"!/n";
    }

    @Test
    public void dateStudty(){
        LocalDate localDate=LocalDate.now();
        System.out.println(localDate);
        //2022-05-12  只有年月日
        LocalTime localTime = LocalTime.now();
        System.out.println(localTime);
        //22:40:24.239991500  只有当天时间具体
        LocalDateTime localDateTime=LocalDateTime.now();
        System.out.println(localDateTime);
        //2022-05-12T22:41:30.446053100 完全时间
        Instant instant=Instant.now();
        System.out.println(instant);
        //完全时间2022-05-12T14:46:02.953516700Z
        System.out.println(instant.getEpochSecond());
        //1652366704 时间戳秒
        LocalDateTime birthDate = LocalDateTime.of(2002,1,16,7,30,0);
        System.out.println(birthDate);

        // 第二个参数减第一个参数
        Duration duration = Duration.between(birthDate,localDateTime);
        // 两个时间差的天数
        System.out.println(duration.toDays());
        // 两个时间差的小时数
        System.out.println(duration.toHours());
        // 两个时间差的分钟数
        System.out.println(duration.toMinutes());
        // 两个时间差的毫秒数
        System.out.println(duration.toMillis());
        // 两个时间差的纳秒数
        System.out.println(duration.toNanos());
    }

    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        String str=sc.nextLine();
        int count=str.length()/8;
        int left=str.length()%8;
        int len=str.length();
        for(int i=0;i<=count;i++){
            int indexEnd=(i+1)*8-1;
            if(len<indexEnd){
                String out=str.substring(indexEnd-7,len);
                for(int j=0;j<8-left;j++){
                    out+="0";
                }
                System.out.print(out+"\n");
            }else{
                String out=str.substring(indexEnd-7,indexEnd);
                System.out.print(out+"\n");
            }
        }

    }
}
