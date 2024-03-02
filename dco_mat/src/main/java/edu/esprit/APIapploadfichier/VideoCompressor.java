package edu.esprit.APIapploadfichier;

import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;

public class VideoCompressor {

    public static String compressVideo(String inputFilePath, String extension) {
        String outputFilePath = inputFilePath + "_compressed." + extension;
        try (FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(inputFilePath)) {

            grabber.start();

            try (FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(outputFilePath, grabber.getImageWidth() / 4, grabber.getImageHeight() / 4)) {
                recorder.setFormat(extension); // Set the format according to the extension
                recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264); // Use H.264 codec
                recorder.setFrameRate(grabber.getFrameRate()); // Use the same frame rate as the input video
                recorder.setVideoBitrate(grabber.getVideoBitrate() / 4); // Use quarter the bit rate of the input video
                recorder.setAudioChannels(1); // Set the number of audio channels
                recorder.setAudioBitrate(grabber.getAudioBitrate()); // Set the audio bitrate
                recorder.setSampleRate(grabber.getSampleRate()); // Set the sample rate
                recorder.start();

                Frame frame;
                while ((frame = grabber.grab()) != null) {
                    recorder.record(frame);
                }

                recorder.stop();
            }

            grabber.stop();


        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("terminer");
        return outputFilePath;
    }



}
