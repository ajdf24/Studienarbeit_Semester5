if(!selectedSound.isInternalSound()){
	Thread thread = new Thread(){
		@Override
		public void run(){
			long currentTime = System.currentTimeMillis();
			while(currentTime+mediaPlayer.getDuration()>System.currentTimeMillis()){

		}
		mediaPlayer.stop();
		Thread.currentThread().interrupt();
		return;
		}
	};
	thread.start();
}