package new_sem.lab_4;

import java.util.ArrayList;
import java.util.Scanner;
 
// Узел цепи
public class HashNode<K, V> {
    K key; // Ключ
    V value; // Значение
 
    // Ссылка на следующий узел
    HashNode<K, V> next;
 
    // Конструктор
    public HashNode(K key, V value)
    {
        this.key = key;
        this.value = value;
    }
}
 
// Класс для представления всей хеш-таблицы
class Map<K, V> {

    // bucketArray используется для хранения массива цепей
    private ArrayList<HashNode<K, V> > bucketArray;
 
    // Текущая ёмкость массива
    private int numBuckets;
 
    // Текущий размер массива
    private int size;

    double loadfactor;
    double newloadfactor;
 
    // Конструктор (Инициализирует ёмкость, размер и пустые цепи).
    public Map()
    {
        bucketArray = new ArrayList<>();
        numBuckets = 10;
        size = 0;
 
        // Создаёт пустые цепи
        for (int i = 0; i < numBuckets; i++)
            bucketArray.add(null);
    }
 
    public int size() { return size; }
    public boolean isEmpty() { return size() == 0; }
 
    // Хеш-функция для поиска индекса ключа
    private int getBucketIndex(K key)
    {
        // При добавлении элемента e выбирать список по формуле: hash(e) mod (число списков);
        int hashCode = key.hashCode();
        int index = hashCode % numBuckets;

        // key.hashCode() может быть отрицательным
        index = index < 0 ? index * -1 : index;
        return index;
    }
 
    // Метод для удаления по ключу
    public V remove(K key)
    {
        // Ищем индекс для данного ключа
        int bucketIndex = getBucketIndex(key);
 
        // Получение головы цепи
        HashNode<K, V> head = bucketArray.get(bucketIndex);
 
        // ПОиск ключа в цепи
        HashNode<K, V> prev = null;
        while (head != null) {
            
            // Если ключ найден
            if (head.key.equals(key))
                break;
            
            // Двигаемся по цепи
            prev = head;
            head = head.next;
        }
 
        // Если ключ не сдесь
        if (head == null){
                System.out.println("элемент с ключом " + key + " - не существует");
            return null;
        }
 
        // Уменьшаем размер
        size--;
 
        // Удаление ключа
        if (prev != null)
            prev.next = head.next;
        
        else
            bucketArray.set(bucketIndex, head.next);
        
        System.out.println("элемент с ключом " + key + " был удалён");
        return head.value;
        
    }
 
    // Метод для получения значения по ключу
    public V get(K key)
    {
        // Поиск головы цепи для данного ключа
        int bucketIndex = getBucketIndex(key);
        HashNode<K, V> head = bucketArray.get(bucketIndex);
 
        // Поиск ключа в цепи
        while (head != null) {
            if (head.key.equals(key)){
                    System.out.println("Значение для ключа " + key + ": " + head.value);
                return head.value;
            }
            head = head.next;
        }

        // Если ключ не был найден
        if (head == null){
            System.out.println("Ключ " + key + " - не существует");
        }
 
        return null;
    }
 
    // Метод для добавления пары ключ-значение
    public void add(K key, V value)
    {
        // Ищем голову цепи для данного ключа
        int bucketIndex = getBucketIndex(key);
        HashNode<K, V> head = bucketArray.get(bucketIndex);
 
        // Проверка, существует ли уже данный ключ или нет
        while (head != null) {
            if (head.key.equals(key)) {
                head.value = value;
                return;
            }
            head = head.next;
        }
 
        // Вставка ключа в цепь
        size++;
        head = bucketArray.get(bucketIndex);
        HashNode<K, V> newNode = new HashNode<K, V>(key, value);
        newNode.next = head;
        bucketArray.set(bucketIndex, newNode);
 
        // Получем значение коэффициента загруженности
        getLoadFactor();

        // Если коэффициент загруженности превышает заданное значение, производим изменение размеров таблицы
        if (loadfactor >= newloadfactor) {
            resize();

            // Производим перехеширование
            reHash();
        }

    }

    // Метод для перехеширования таблицы
    private void reHash(){

         ArrayList<HashNode<K, V> > temp = bucketArray;
         bucketArray = new ArrayList<>();

         // Первоначальное колличество элементов = 0
         size = 0;
         for (int i = 0; i < numBuckets; i++)
             bucketArray.add(null);

         // Добавляем элементы в новый(изменённый) массив
         for (HashNode<K, V> headNode : temp) {
             while (headNode != null) {
                 add(headNode.key, headNode.value);
                 headNode = headNode.next;
             }
         }
    }

    // Метод для изменения коэффициента загруженности
    public double LFchange(){

        System.out.println("Нажмите 1, что бы изменить коэффициент загруженности | Нажмите 2, что бы оставить прежний коэффициент загруженности");

        final Scanner in = new Scanner(System.in);
        final int i = in.nextInt();
            
            if(i == 1){
                System.out.println("Введите новое значение коэффициента загруженности");

                final double a = in.nextDouble();
                in.close();

                newloadfactor = a;
                System.out.println("Новый коэффициент загруженности: " + newloadfactor);
            }

            if(i == 2){
                newloadfactor = 2;
            return newloadfactor;
            }

            return newloadfactor;
    }

    // Метод для изменения ёмкости массива
    private void resize(){ 
        numBuckets = 2 * numBuckets + 1;
    }

    // Метод для получения коэффициента загруженности
    public double getLoadFactor(){ 

            loadfactor = (1.0 * size) / numBuckets;

        return loadfactor;
    }

    // Метод для удаления всех элементов
    public void resetHashTable() {
        this.size = 0;
        this.numBuckets = this.size;
        this.bucketArray = new ArrayList<>(this.numBuckets);

        System.out.println("Все элементы были удалены");
    }
    
    // Метод для вывода элементов таблицы
    public void printBuckets() {
		
		for (int i = 0; i < bucketArray.size(); i++) {

			HashNode<K,V> head = bucketArray.get(i);
			System.out.print("Список с индексом " + i + ": ");

			while (head != null) {
				System.out.print(head.value);
				head = head.next;
            } 
            
			System.out.println();
		} 
	}
}