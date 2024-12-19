package playSound;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.BitstreamException;
import javazoom.jl.decoder.Decoder;
import javazoom.jl.decoder.DecoderException;
import javazoom.jl.decoder.Header;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.decoder.SampleBuffer;
import javazoom.jl.player.AudioDevice;
import javazoom.jl.player.FactoryRegistry;

public class MyPlayer {
	public static final int BUFFER_SIZE = 44100000;
	private Decoder decoder;
	private AudioDevice out;
	private ArrayList<Sample> samples;
	private short[][] musicbuffers;
	private int size;
	private volatile boolean stop = false;  // 음악 재생을 중지시킬 때 true로 설정됨
	
	public MyPlayer(String path) {
		Open(path);
	}
	
	private short[][] GetMusicBuffers() {
		return musicbuffers;
	}
	
	public boolean IsInvalid() {
		return (decoder == null || out == null || samples == null || !out.isOpen());
	}
	
	public boolean Open(String path) {
		Close();
		try {
			decoder = new Decoder();
			out = FactoryRegistry.systemRegistry().createAudioDevice();
			samples = new ArrayList<Sample>(BUFFER_SIZE);
			size=0;
			out.open(decoder);
			GetSamples(path);
			musicbuffers = GetMusicBuffers();
		} catch(JavaLayerException e) {
			decoder = null;
			out = null;
			return false;
		}
		return true;
	}

	public void Close() {
		if((out != null) && !out.isOpen()) {
			out.close();
		}
		size=0;
		samples = null;
		out = null;
		decoder = null;
	}
	
	protected boolean GetSamples(String path) {
	    if(IsInvalid())
	        return false;
	    try {
	        Header header;
	        SampleBuffer sb;
	        FileInputStream in = new FileInputStream(path);
	        Bitstream bitstream = new Bitstream(in);
	        if((header = bitstream.readFrame()) == null)
	            return false;
	        while(size < BUFFER_SIZE && header != null) {
	            sb = (SampleBuffer)decoder.decodeFrame(header, bitstream);
	            samples.add(new Sample(sb.getBuffer(), sb.getBufferLength()));
	            size++;
	            bitstream.closeFrame();
	            header = bitstream.readFrame();
	        }
	    } catch(FileNotFoundException e) {
	        return false;
	    } catch(BitstreamException e) {
	        return false;
	    } catch(DecoderException e) {
	        return false;
	    }
	    return true;
	}
	
	public void stop() {
        stop = true;  // 음악 재생 중지
    }
	
	public void Play() {
        if(IsInvalid())
            return;
        try {
            for(int i = 0; i < size; i++) {
                if (stop) break;  // stop이 true이면 음악 재생 중지
                short[] buffers = samples.get(i).GetBuffer();
                if(out != null) {
                    out.write(samples.get(i).GetBuffer(), 0, samples.get(i).GetSize());
                }
            }
            if(out != null) {
                out.flush();
            }
        } catch(JavaLayerException e) {
            e.printStackTrace();
        }
    }
}
