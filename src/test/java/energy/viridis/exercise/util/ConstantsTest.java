package energy.viridis.exercise.util;

import org.junit.Assert;
import org.junit.Test;

public class ConstantsTest {

    @Test
    public void msgErrorSavingRecord(){
        Assert.assertEquals("Error saving record", Constants.MSG_ERROR_SAVING_RECORD);
    }

    @Test
    public void msgSuccessufullyRemoved(){
        Assert.assertEquals("Record successfully removed", Constants.MSG_SUCESSFULY_REMOVED);
    }
}
