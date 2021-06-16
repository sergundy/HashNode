package new_sem.lab_4;

public class Htest {
     
    public static void main(String[] args)
    {
        Map<Integer, String> map = new Map<>();

        // Изменение коэффициента загруженности
        map.LFchange();

        for(int i = 0; i < 100; i++){
            map.add(i, "a");
        }

        System.out.println("\n");
        System.out.println("────────────────────────────────────────────────────────");
        System.out.println("\n");

        // Вывод элементов таблицы
        map.printBuckets();

        System.out.println("\nРазмер массива: " + map.size());// Вывод количества элементов
        System.out.println("\nКоэффициент загруженности: " + map.getLoadFactor());// Вывод значения коэффициента загруженности

        System.out.println("\n");
        System.out.println("────────────────────────────────────────────────────────");
        System.out.println("\n");

        map.get(1);// Получение значения по ключу
        map.get(1000);// Получение значения по ключу
        map.get(1001);// Получение значения по ключу
        map.get(1002);// Получение значения по ключу
        map.get(1003);// Получение значения по ключу

        System.out.println("\n");
        System.out.println("────────────────────────────────────────────────────────");
        System.out.println("\n");

        for(int i = 0; i < 5; i++){
            map.remove(i);
        }
        map.remove(1000);

        System.out.println("\n");

        System.out.println("\nРазмер массива после удаления элементов: " + map.size());
        System.out.println("\nКоэффициент загруженности: " + map.getLoadFactor());
        
        System.out.println("\n");
        System.out.println("────────────────────────────────────────────────────────");
        System.out.println("\n");

        // Удаление всех элементов
        map.resetHashTable();

        System.out.println("\nРазмер массива после удаления всех элементов: " + map.size());
        System.out.println("\nКоэффициент загруженности: " + map.getLoadFactor());

        System.out.println("\n");
        System.out.println("────────────────────────────────────────────────────────");
        System.out.println("\n");
    }
}
