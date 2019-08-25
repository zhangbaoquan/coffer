package coffer.filedemo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;

import coffer.androidjatpack.R;
import coffer.util.CONSTANT;
import coffer.util.FileUtils;

/**
 * @author：张宝全
 * @date：2019-08-23
 * @Description：
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class FileActivity extends AppCompatActivity {

    private TextView mTextView;
    private String mFileContent = "OKHttp源码解析(四)--中阶之拦截器及调用链";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_main);

        init();

    }

    private void init() {

        mTextView = findViewById(R.id.t1);
        // 将pic目录下图片复制到assert文件里的
        findViewById(R.id.b1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyFromPicToAssert();
            }
        });

        // 将assert文件里的图片复制到pic目录下
        findViewById(R.id.b5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyFromPicToAssert();
            }
        });

        // 文件追加写
        findViewById(R.id.b2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fileWriteRandom();
            }
        });

        // 文件覆盖写
        findViewById(R.id.b4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fileWriteCover();
            }
        });

        // 文件读
        findViewById(R.id.b3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fileRead();
            }
        });
    }

    /**
     * 文件读写
     */
    private void fileRead() {
        InputStreamReader reader = null;
        BufferedReader bufferedReader = null;
        String lineContent;
        StringBuffer buffer = new StringBuffer();
        try {
            String path = FileUtils.getTxtPath() + "coffer2.txt";
            File file = FileUtils.createFile(path);
            if (file != null){
                // 读取字节流，以UTF_8方式编码
                reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
                bufferedReader = new BufferedReader(reader);
                while ((lineContent = bufferedReader.readLine()) != null){
                    buffer.append(lineContent);
                }
                Log.i(CONSTANT.COFFER_TAG,"TXT文件内容： "+buffer.toString());
                mTextView.setText(buffer.toString());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (reader != null && bufferedReader != null){
                try {
                    reader.close();
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 创建文件并写文件
     */
    private void fileWriteRandom() {
        try {
            String txt_file = FileUtils.getTxtPath() + "coffer.txt";
            // 创建一个coffer.txt的文件
            File file = FileUtils.createFile(txt_file);
            if (file != null) {
                Log.i(CONSTANT.COFFER_TAG,"创建TXT文件成功，文件路径 ： "+file.getAbsolutePath());
                writeRandomAccess(file,"啦啦啦"+"\n");
            }
        }catch (Exception e){
            Log.i(CONSTANT.COFFER_TAG,"创建TXT文件异常： "+e.getMessage());
        }
    }

    /**
     * 在原有的内容上追加内容
     * @param file
     */
    private void writeRandomAccess(File file,String content){
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file,"rw");
            // 将光标移动到文件的最后，再次写入
            randomAccessFile.seek(file.length());
            randomAccessFile.write(content.getBytes());
            randomAccessFile.close();
        } catch (FileNotFoundException e) {
            Log.i(CONSTANT.COFFER_TAG,"TXT文件异常： "+e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            Log.i(CONSTANT.COFFER_TAG,"TXT文件追加写入异常： "+e.getMessage());
        }
    }

    /**
     *
     */
    private void fileWriteCover(){
        String txt_file2 = FileUtils.getTxtPath() + "coffer2.txt";
        File file = FileUtils.createFile(txt_file2);
        if (file != null){
            Log.i(CONSTANT.COFFER_TAG,"创建TXT 2 文件成功，文件路径 ： "+file.getAbsolutePath());
            writeCover(file,mFileContent);
        }
    }

    /***
     * 先清空内容再写入
     */
    private void writeCover(File file,String content){
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            // 将要写入的数据转换成二进制数据
            byte[] bytes = content.getBytes();
            fileOutputStream.write(bytes);
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            Log.i(CONSTANT.COFFER_TAG,"TXT 2 文件异常： "+e.getMessage());
        } catch (IOException e) {
            Log.i(CONSTANT.COFFER_TAG,"TXT 2 写入文件异常： "+e.getMessage());
        } finally {
            if (fileOutputStream != null){
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    Log.i(CONSTANT.COFFER_TAG,"TXT 2 关闭文件异常： "+e.getMessage());
                }
            }
        }
    }

    /**
     * 将assert文件里的图片复制到pic目录下
     */
    private void copyFromAssertToPic() {

    }

    /**
     * 将pic目录下图片复制到assert文件里的
     */
    private void copyFromPicToAssert() {

    }


}
