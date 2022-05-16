import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.wanjun.CallableThread;
import com.wanjun.Producer;
import org.junit.Test;

import java.time.*;
import java.util.*;
import java.util.concurrent.*;

/**
 * @author wanjun
 * @create 2022-04-14 16:04
 */
public class hello {
    // 用guava的threadfactory可以给线程池命名，如果不需要这个参数可以去掉
    private static ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("fuck").build();
    //JDK中默认使用的线程池 ThreadPoolExecutor
    //这里默认拒绝策略为AbortPolicy
    //corePoolSize-核心线程数
    //maximumPoolSize-最大线程数
    //keepalivetime-非核心线程的空闲时间被自动回收的时间长度
    //unit-时间单位
    //workQueue-保存非核心线程的任务的队列
    //threadFactory 一般是默认，也可以用guava的ThreadFactoryBuilder创建比如给这个线程池取名字
    //handler-队列已满且线程数达到最大线程数的拒绝策略，一般用默认AbortPolicy
    //1.创建一个线程池对象
    private static ExecutorService executor = new ThreadPoolExecutor(10,
            10,
            60L,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue(10),threadFactory);

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

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //2.循环创建任务对象
        List<FutureTask<Integer>> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            //Producer producer=new Producer("上传者"+i);
            //无返回值,runnable是没有用的
            //executor.submit(producer);

            //无返回值，只能是runnable线程任务
            //executor.execute(producer);
            //有返回值,线程是继承Callable就可以
            CallableThread callableThread = new CallableThread();
            FutureTask<Integer> futureTask = new FutureTask<>(callableThread);
            list.add(futureTask);
            executor.submit(futureTask);
        }
        for (FutureTask<Integer> futureTask : list) {
            System.out.println("result:" + futureTask.get());
        }
        //3.关闭线程池
        executor.shutdown();
//        Scanner sc=new Scanner(System.in);
//        String str=sc.nextLine();
//        int count=str.length()/8;
//        int left=str.length()%8;
//        int len=str.length();
//        for(int i=0;i<=count;i++){
//            int indexEnd=(i+1)*8-1;
//            if(len<indexEnd){
//                String out=str.substring(indexEnd-7,len);
//                for(int j=0;j<8-left;j++){
//                    out+="0";
//                }
//                System.out.print(out+"\n");
//            }else{
//                String out=str.substring(indexEnd-7,indexEnd);
//                System.out.print(out+"\n");
//            }
//        }

    }
}
