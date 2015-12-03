package internetconnection;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Connection {

	public static void main(String[] args) {

		try {
			final URL url = new URL("https://google.com/");

			List<Double> pingCollection = new ArrayList<Double>();
			
			String directory = System.getProperty("user.home") + File.separator + "ping3.txt";
			final File file = new File(directory);
			FileWriter fw = new FileWriter(file);

			runForTime(url, 21600000, fw);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void request(final URL url) throws IOException {
		final InputStream stream = url.openStream();
	}

	private static double timeToConnect(final URL url) throws IOException {
		final double startTime = System.currentTimeMillis();
		request(url);
		final double finishTime = System.currentTimeMillis();

		return finishTime - startTime;
	}

	private static void runForTime(URL url, long time, FileWriter fw)
			throws FileNotFoundException, UnsupportedEncodingException {

		TimerTask timerTask = new TimerTask() {

			@Override
			public void run() {
				double ping = 0;
				try {
					ping = timeToConnect(url);
					fw.write(Double.toString(ping) + ", ");
					fw.flush();

					System.out.println(ping);
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.println(ping);
			}

		};

		Timer timer = new Timer();

		TimerTask timerStop = new TimerTask() {
			@Override
			public void run() {
				timer.cancel();
				System.out.println("Finished.");
			}
		};

		timer.scheduleAtFixedRate(timerTask, 0, 1000);
		timer.schedule(timerStop, time);
	}
}
