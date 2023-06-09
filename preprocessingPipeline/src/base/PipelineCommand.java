package base;

//@{}
public interface PipelineCommand {

	public void launchPipeline();
	public void cleanData();
	public void fillMissingValues();
	public void encodeCategorical();
	public DataCarrier transformData(DataCarrier data);
}
