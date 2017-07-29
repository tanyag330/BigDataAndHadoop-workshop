package ultimatetutorial;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;

public class InvertedIndexReducer extends Reducer<Text,Text,Text,Text> {

    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        List<String> valueList = new LinkedList<String>();
        for (Text value : values) {
            String valueAsString = new String(value.getBytes(), Charset.forName("UTF-8"));
            if (!valueList.contains(valueAsString)) {
                valueList.add(value.toString());
            }
        }
        StringBuilder sb = new StringBuilder();
        for (String value : valueList) {
            if (sb.length() > 0) {
                sb.append(",");
            }
            sb.append(value);
        }
        context.write(key, new Text(sb.toString()));
    }
}
