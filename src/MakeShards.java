import java.io.BufferedReader;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class MakeShards {
    private static final int SHARD_SIZE = 100;

    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.out.println("Usage: MakeShards [input file] [output folder]");
            return;
        }

        Path input = Path.of(args[0]);
        Path outputFolder = Files.createDirectory(Path.of(args[1]));

        // TODO: Read the unsorted words from the "input" Path, line by line. Write the input words to
        //       many shard files. Each shard file should contain at most SHARD_SIZE words, in sorted
        //       order. All the words should be accounted for in the output shard files; you should not
        //       skip any words. Write the shard files in the newly created "outputFolder", using the
        //       getOutputFileName(int) method to name the individual shard files.

        char[] data = new char[SHARD_SIZE];
        BufferedReader reader = Files.newBufferedReader(input, StandardCharsets.UTF_8);
        int counter = 0;
        String word = reader.readLine();
        while(word != null) {
            counter++;
            List<String> shards = new ArrayList<String>();
            while(word!=null && shards.size() < SHARD_SIZE){
                shards.add(word);
                word = reader.readLine();
            }
            shards.sort(String::compareTo);
            Path outFile = Path.of(args[1], getOutputFileName(counter));
            Writer writer = Files.newBufferedWriter(outFile, StandardCharsets.UTF_8);
            for (int i = 0; i < shards.size(); i++) {
                writer.write(shards.get(i));
                if (i < shards.size() - 1) {
                    writer.write(System.lineSeparator());
                }
            }
            writer.close();  // Close the "test" file
        }
        reader.close();
    }
    private static String getOutputFileName ( int shardNum){
        return String.format("shard%02d.txt", shardNum);
    }
}


