package it.adbmime.devices;

import it.adbmime.AdbHelper;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class DeviceConnect {
    protected static final String DEVICE_CONNECT = "adb connect ";

    public static void connect(int ip1, int ip2, int ip3, int ip4, int port) {
        List<Integer> list1 = ip1 != -1 ? Arrays.asList(ip1) : interval(0, 255);
        List<Integer> list2 = ip2 != -1 ? Arrays.asList(ip2) : interval(0, 255);
        List<Integer> list3 = ip3 != -1 ? Arrays.asList(ip3) : interval(0, 255);
        List<Integer> list4 = ip4 != -1 ? Arrays.asList(ip4) : interval(0, 255);

        List<List<Integer>> input = Arrays.asList(list1, list2, list3, list4);

        CartesianProduct<Integer> cartesianProduct = new CartesianProduct<>();
        List<List<Integer>> ips = cartesianProduct.product(input);

        ips.parallelStream().forEach(
                ip -> {
                    int IP1 = ip.get(0);
                    int IP2 = ip.get(1);
                    int IP3 = ip.get(2);
                    int IP4 = ip.get(3);

                    Thread t = new Thread(() -> {
                        String ipPort = IP1 + "." + IP2 + "." + IP3 + "." + IP4 + ":" + port;
                        AdbHelper.run(DEVICE_CONNECT + ipPort);
                    });
                    t.start();
                    try {
                        t.join(50);
                        t.stop();
                    } catch (InterruptedException e) {
                    }
                }
        );
//        for(List<Integer> ip: ips){
//            int IP1 = ip.get(0);
//            int IP2 = ip.get(1);
//            int IP3 = ip.get(2);
//            int IP4 = ip.get(3);
//
//            String ipPort = IP1 + "." + IP2 + "." + IP3 + "." + IP4 + ":" + port;
//            AdbHelper.run(DEVICE_CONNECT + ipPort, 10);
//        }
    }

    /**
     * @see https://www.baeldung.com/java-listing-numbers-within-a-range
     * @param start
     * @param end
     * @return
     */
    private static List<Integer> interval(int start, int end) {
        return IntStream.rangeClosed(start, end)
                .boxed()
                .collect(Collectors.toList());
    }

}
