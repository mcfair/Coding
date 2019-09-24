class LRUCache {
    List<Entry> list;
    int initialCapacity;
    int count;
    
    public LRUCache(int capacity) {
        this.list = new LinkedList<>();
        this.initialCapacity = capacity;
        this.count = 0;
    }
    
    public int get(int key) {
        for (Entry entry : list) {
            if (entry.key == key) {
                list.remove(entry);
                list.add(0, entry);
                return entry.value;
            }
        }
        return -1;
    }
    
    public void put(int key, int value) {
        for (Entry entry : list) {
            if (entry.key == key) {
                entry.value = value;
                list.remove(entry);
                list.add(0, entry);
                return;
            }
        }
        
        list.add(0, new Entry(key, value));
        count++;
        if (count > initialCapacity) {
            list.remove(initialCapacity);
        }
    }
    
    class Entry {
        int key;
        int value;
        public Entry(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
