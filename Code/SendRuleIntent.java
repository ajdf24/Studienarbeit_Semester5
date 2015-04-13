// Create the intent
final Intent intent = new Intent(Intent.ACTION_SEND);

// set the MIME type and grant access to the uri (for the attached file, although I'm not sure if the grant access is required)
Intent.setType("text/plain");

// Copy file to external storage
File publicFile = new File("/sdcard/" + selectedRule.getRuleName());
try {
    InputStream initialStream = new FileInputStream(new File(RuleObserver.getUriFromSMSRule(selectedRule.getRuleName()).getPath()));
    byte[] buffer = new byte[initialStream.available()];
    initialStream.read(buffer);

    OutputStream outStream = new FileOutputStream(publicFile);
    outStream.write(buffer);
    initialStream.close();
    outStream.close();
} catch (Exception e) {
    Log.e("Can not write", e.getMessage());
}

// Get the Uri from the external file and add it to the intent
Uri uri = Uri.fromFile(publicFile);
intent.putExtra(Intent.EXTRA_STREAM, uri);

this.startActivity(Intent.createChooser(intent, CreateContextForResource.getStringFromID(R.string.activity_rule_selection_context_menu_selection_title)));
