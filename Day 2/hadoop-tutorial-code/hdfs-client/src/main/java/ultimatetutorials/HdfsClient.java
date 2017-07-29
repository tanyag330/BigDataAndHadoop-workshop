package ultimatetutorials;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;

import java.io.IOException;

public class HdfsClient {

    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.err.println("Please provide the HDFS path (hdfs://hosthdfs:port/path)");
            return;
        }
        String hadoopConf = System.getProperty("hadoop.conf");
        if (hadoopConf == null) {
            System.err.println("Please provide the system property hadoop.conf");
            return;
        }
        Configuration conf = new Configuration();
        conf.addResource(new Path(hadoopConf + "/core-site.xml"));
        conf.addResource(new Path(hadoopConf + "/hdfs-site.xml"));
        conf.addResource(new Path(hadoopConf + "/mapred-site.xml"));
        conf.set("fs.hdfs.impl", org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
        conf.set("fs.file.impl", org.apache.hadoop.fs.LocalFileSystem.class.getName());
        FileSystem fileSystem = FileSystem.get(conf);
        RemoteIterator<LocatedFileStatus> iterator = fileSystem.listFiles(new Path(args[0]), false);
        while (iterator.hasNext()) {
            LocatedFileStatus fileStatus = iterator.next();
            Path path = fileStatus.getPath();
            System.out.println(path);
        }
    }
}
