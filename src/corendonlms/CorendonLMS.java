package corendonlms;

/**
 *
 * @author Emile Pels
 */
public class CorendonLMS
{    
    private static final CorendonLMS APPLICATION = new CorendonLMS();
    
    /**
     * Gets the instance of the currently executing application
     * @return The instance of the currently executing application
     */
    public static CorendonLMS getInstance()
    {
        return APPLICATION;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        
    }
    
}
