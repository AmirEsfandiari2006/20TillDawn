package Models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.image.BufferedImage;
import java.io.File;

public interface FilePicker {
    default void chooseImage(ImagePickerCallback callback){
            SwingUtilities.invokeLater(() -> {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileFilter(new FileNameExtensionFilter("Images", "jpg", "png", "jpeg", "bmp"));

                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();

                    File destFile = getFile();

                    try {
                        // Convert to PNG if necessary
                        BufferedImage inputImage = javax.imageio.ImageIO.read(selectedFile);
                        javax.imageio.ImageIO.write(inputImage, "png", destFile);

                        // Return FileHandle
                        callback.onImageSelected(Gdx.files.absolute(destFile.getAbsolutePath()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
    }

    private static File getFile() {
        File uploadDir = new File("data/uploadedImage/");
        if (!uploadDir.exists()) {
            uploadDir.mkdirs(); // Create directory if not present
        }

        // Find next available uX.png filename
        int nextIndex = 1;
        while (new File(uploadDir, "u" + nextIndex + ".png").exists()) {
            nextIndex++;
        }

        // Save as PNG regardless of original extension
        File destFile = new File(uploadDir, "u" + nextIndex + ".png");
        return destFile;
    }

    interface ImagePickerCallback {
        void onImageSelected(FileHandle fileHandle);
    }
}
