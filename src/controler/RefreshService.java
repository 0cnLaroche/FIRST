package controler;

import first.FIRST;
import javafx.application.Platform;
/**
 * Tells the DataLayer to download data from the database and reload it in into the app at a given frequency.
 * Default frequency of download is every 100 seconds. Will notify the user on the GUI if the connection
 * is lost and will attempt to reconnect again until it is connected again.
 * @author samuel.laroche
 *
 */
public class RefreshService extends Thread {

	private FIRST main;
	private DataLayer manager;
	private int frequency;

	public RefreshService(FIRST main) {
		this.main = main;
		this.manager = main.getManager();
		this.frequency = 100000; // Milliseconds or 100 sec
	}

	public void run() {

		while (true) {

			try {
				Thread.sleep(frequency);
				if (manager.isConnected()) {
					manager.load();
				} else {

					Platform.runLater(new Runnable() {

						// Will be run later by the JavaFx thread
						@Override
						public void run() {
							main.notifyUser("Connection Lost");
						}

					});
					while (true) {
						// Try to reconnect
						try {
							manager.connect();
							break;

						} catch (DatabaseCommunicationsException e) {

							Platform.runLater(new Runnable() {
								// Will be run later by the JavaFx thread
								@Override
								public void run() {
									main.notifyUser("Server unreachable, trying again in 15 seconds");
								}

							});
							// Wait 15 before trying again
							Thread.sleep(15000);

						} catch (Exception e2) {
							e2.printStackTrace();
						}

					}

				}

			} catch (Exception e) {
				e.printStackTrace();

			}
		}

	}
	/**
	 * Sets the time between each execution of a refresh.
	 * @param seconds Number of seconds
	 */
	public void setFrequency(int seconds) {
		this.frequency = seconds * 1000;
	}
}
