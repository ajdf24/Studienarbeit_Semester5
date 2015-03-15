public class RuleObserver {

    /**
     * This method saves a rule to the file system.
     * If the file is already exists, so the new state is saved over the old one.
     * The method saves instances of {@link rieger.alarmsmsapp.model.rules.EMailRule} and {@link rieger.alarmsmsapp.model.rules.SMSRule}
     * in different folders.
     * @param rule the instance of a {@link rieger.alarmsmsapp.model.rules.Rule} which should be saved.
     */
	public static void saveRuleToFileSystem(Rule rule){
		ObjectMapper mapper = new ObjectMapper();

		if(rule instanceof SMSRule){
			File smsRuleDirectory = CreateContextForResource.getContext().getDir(AppConstants.StringsForObserver.DIRECTORY_NAME_SMS_RULES, Context.MODE_PRIVATE);
			File smsRuleFile = new File(smsRuleDirectory, rule.getRuleName());
			try {
				mapper.writeValue(smsRuleFile, rule);
			} catch (IOException e) {
				Log.e(AppConstants.DEBUG_TAG, "Error while writing rule to file system",  e);
			}
		}
		if (rule instanceof EMailRule) {
			File smsRuleDirectory = CreateContextForResource.getContext().getDir(AppConstants.StringsForObserver.DIRECTORY_NAME_MAIL_RULES, Context.MODE_PRIVATE);
			File smsRuleFile = new File(smsRuleDirectory, rule.getRuleName());
			try {
				mapper.writeValue(smsRuleFile, rule);
			} catch (IOException e) {
				Log.e(AppConstants.DEBUG_TAG, "Error while writing rule to file system",  e);
			}
		}


	}

    /**
     * This method reads all rule files from the file system and convert these to instances of the class
     * {@link rieger.alarmsmsapp.model.rules.Rule}.
     * @return a list of all rules which are currently saved in the file system.
     */
	public static List<Rule> readAllRulesFromFileSystem() {

		File smsFolder = CreateContextForResource.getContext().getDir(AppConstants.StringsForObserver.DIRECTORY_NAME_SMS_RULES, Context.MODE_PRIVATE);
		File[] listOfSMSFiles = smsFolder.listFiles();

		File MailFolder = CreateContextForResource.getContext().getDir(AppConstants.StringsForObserver.DIRECTORY_NAME_MAIL_RULES, Context.MODE_PRIVATE);
		File[] listOfMailFiles = MailFolder.listFiles();

		ObjectMapper mapper = new ObjectMapper();

		List<Rule> ruleList = new ArrayList<Rule>();

		for (File fileWithRule : listOfSMSFiles) {

			if (fileWithRule.isFile()) {
				try {
					ruleList.add(mapper.readValue(fileWithRule, SMSRule.class));
				} catch (IOException e) {
					Log.e(AppConstants.DEBUG_TAG, "Can not read Files.", e);
				}
			}
		}
		for (File fileWithRule : listOfMailFiles) {

			if (fileWithRule.isFile()) {
				try {
					ruleList.add(mapper.readValue(fileWithRule, EMailRule.class));
				} catch (IOException e) {
					Log.e(AppConstants.DEBUG_TAG, "Can not read Files.", e);
				}
			}
		}

		return ruleList;
	}

    /**
     * This method delete a rule from the file system. If the rule not exists so this method gives a
     * log message but throw not an exception.
     * @param rule the rule which should be saved
     */
	public static void deleteRuleFromFilesystem(Rule rule){

		if (rule instanceof SMSRule) {

			String ruleFilePath = CreateContextForResource.getContext().getDir(AppConstants.StringsForObserver.DIRECTORY_NAME_SMS_RULES, Context.MODE_PRIVATE) + "/" + rule.getRuleName();

			File ruleFile = new File( ruleFilePath );
			if(ruleFile.delete()){
			}else{
				Log.e(AppConstants.DEBUG_TAG, "Delete operation is failed.");
			}
		}
		if (rule instanceof EMailRule) {
			String ruleFilePath = CreateContextForResource.getContext().getDir(AppConstants.StringsForObserver.DIRECTORY_NAME_MAIL_RULES, Context.MODE_PRIVATE) + "/" + rule.getRuleName();

			File ruleFile = new File( ruleFilePath );
			if(ruleFile.delete()){
			}else{
				Log.e(AppConstants.DEBUG_TAG, "Delete operation is failed.");
			}
		}
	}

    /**
     * This method reads all sms rule files from the file system and convert these to instances of the class
     * {@link rieger.alarmsmsapp.model.rules.Rule}.
     * @return a list of all sms rules which are currently saved in the file system.
     */
    public static List<Rule> readAllSMSRulesFromFileSystem() {

        File smsFolder = CreateContextForResource.getContext().getDir(AppConstants.StringsForObserver.DIRECTORY_NAME_SMS_RULES, Context.MODE_PRIVATE);
        File[] listOfSMSFiles = smsFolder.listFiles();

        ObjectMapper mapper = new ObjectMapper();

        List<Rule> ruleList = new ArrayList<Rule>();

        for (File fileWithRule : listOfSMSFiles) {

            if (fileWithRule.isFile()) {
                try {
                    ruleList.add(mapper.readValue(fileWithRule, SMSRule.class));
                } catch (IOException e) {
                    Log.e(AppConstants.DEBUG_TAG, "Can not read Files.", e);
                }
            }
        }
        return ruleList;
    }
}
