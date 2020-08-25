public interface IAlgorithm{
	public void init();
	public void initPseudocode();
	public void addCurrentFrame();
	public Frame getCurrentFrame();
	public Frame next();
	public Frame prev();
}