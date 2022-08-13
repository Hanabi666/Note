package preference;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPreference {
    private static SharedPreferences sharedPreferences;
    private static final MySharedPreference instance = new MySharedPreference();
    public static MySharedPreference getInstance(Context context) {
        if(sharedPreferences == null){
            sharedPreferences = context.getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE);
        }
        return instance;
    }
    public void save(String key, Object value){
        if(value instanceof String)
            sharedPreferences.edit().putString(key, (String)value).apply();
        else if(value instanceof Integer)
            sharedPreferences.edit().putInt(key, (Integer)value).apply();
        else if(value instanceof Boolean)
            sharedPreferences.edit().putBoolean(key, (Boolean)value).apply();
    }
    public String getString(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }
    public Boolean getBoolean(String key, Boolean defaultValue){
        return sharedPreferences.getBoolean(key, defaultValue);
    }
    public int getInt(String key, int defaultValue){
        return sharedPreferences.getInt(key, defaultValue);
    }
}
