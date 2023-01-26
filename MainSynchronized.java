package working_with_synchronization.home_work02;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
/**
 * @author Leonid Zulin
 * @date 24.01.2023 21:06
 */
public class MainSynchronized {
    // The number of threads and the number of routes generated.
    private static final int numberOfRoutes = 1000;
    private static final Map<Integer, Integer> sizeToFreq = new HashMap<>();
    private static int value;// random frequencies of the letter 'R'

    public static void main(String[] args) throws InterruptedException {
        int maxValue;
        System.out.println("Main starts!!!");

        // In each thread we generate text and synchronously count the number of
        // commands to the right
        for (int i = 0; i < numberOfRoutes; i++) {
            Thread thread;
            thread = new Thread(() -> {
                String route = generateRoute("RLRFR", 100);
                int resultR = 0;
                for (int j = 0; j < route.length(); j++) {
                    if (route.charAt(j) == 'R') {
                        resultR++;
                    }
                }
                fillSizeToFreq(resultR);
                System.out.println("В строке из 100 символов присутствует " + resultR + " символов R");


            });

            thread.start();
            thread.join();
        }

        System.out.println();
        maxValue = Collections.max(sizeToFreq.values());
        for (Map.Entry<Integer, Integer> set : sizeToFreq.entrySet()) {
            if (set.getValue() == maxValue) {
                System.out.println(
                        "Самое частое количество повторений " + set.getKey() + " (встретилось " + maxValue + " раз)");
            }

        }
        System.out.println("Другие размеры: ");
        for (Map.Entry<Integer, Integer> set : sizeToFreq.entrySet()) {
            if (set.getValue() == maxValue) {
                continue;
            } else {
                System.out.println("- " + set.getKey() + "(" + set.getValue() + " раз)");
            }


        }
        // return to thread main
        System.out.println("Main ends!!!");
    }

    private static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }

    // Synchronized map filling method
    private synchronized static HashMap<Integer, Integer> fillSizeToFreq(int countOfR) {
        if (sizeToFreq.containsKey(countOfR)) {
            value = sizeToFreq.get(countOfR) + 1;
        } else {
            value = 1;
        }
        sizeToFreq.put(countOfR, value);
        return (HashMap<Integer, Integer>) sizeToFreq;
    }
}
