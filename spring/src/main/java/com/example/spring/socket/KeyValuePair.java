package com.example.spring.socket;

/**泛型数据类
 * @author wanjun
 * @create 2022-10-05 15:22
 */
public class KeyValuePair<TKey,TValue> {
    private TKey key;
    private TValue value;

    public KeyValuePair(TKey key, TValue value) {
        this.key = key;
        this.value = value;
    }

    public KeyValuePair() {
    }

    public TKey getKey() {
        return key;
    }

    public void setKey(TKey key) {
        this.key = key;
    }

    public TValue getValue() {
        return value;
    }

    public void setValue(TValue value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object obj){
        return super.equals(obj);
    }

    @Override
    public String toString(){
        return "key:"+key+" value:"+value;
    }
}
