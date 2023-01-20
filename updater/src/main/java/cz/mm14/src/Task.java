package cz.mm14.src;

import javax.swing.SwingWorker;

class Task extends SwingWorker<Void, Void> 
{
	private final Updater updater;
	private String task;

	Task(Updater updater, String task) {
		this.updater = updater;
		this.task = task;
	}

	@Override
    public Void doInBackground() {
		if(task == "download")
		{
			DownloadTask.work(updater);
		}
		
		if(task == "delete")
		{
			DeleteTask.work(updater);
		}
		
		return null;
    }
}