public synchronized static ArrayList<String> autocomplete(String input) {
		ArrayList<String> resultList = null;
		HttpURLConnection connection = null;
		StringBuilder jsonResults = new StringBuilder();
		try {
			StringBuilder stringBuilder = new StringBuilder(
					AppConstants.PalcesAPIStrings.PLACES_API_BASE
							+ AppConstants.PalcesAPIStrings.TYPE_AUTOCOMPLETE
							+ AppConstants.PalcesAPIStrings.OUT_JSON);
			stringBuilder.append("?key=" + AppConstants.API_KEY);
			stringBuilder.append("&components=country:de");
			stringBuilder.append("&input=" + URLEncoder.encode(input, "utf8"));
			URL url = new URL(stringBuilder.toString());
			connection = (HttpURLConnection) url.openConnection();
			InputStreamReader inputStream = new InputStreamReader(
					connection.getInputStream());
			// Load the results into a StringBuilder
			int read;
			char[] buffer = new char[1024];
			while ((read = inputStream.read(buffer)) != -1) {
				jsonResults.append(buffer, 0, read);
			}
		} catch (MalformedURLException e) {
			Log.e(AppConstants.DEBUG_TAG, "Error processing Places API URL", e);
			return resultList;
		} catch (IOException e) {
			Log.e(AppConstants.DEBUG_TAG, "Error connecting to Places API", e);
			return resultList;
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
		try {
			// Create a JSON object hierarchy from the results
			JSONObject jsonObject = new JSONObject(jsonResults.toString());
			JSONArray predictionsJsonArray = jsonObject
					.getJSONArray("predictions");
			// Extract the Place descriptions from the results
			resultList = new ArrayList<String>(predictionsJsonArray.length());
			for (int i = 0; i < predictionsJsonArray.length(); i++) {
				resultList.add(predictionsJsonArray.getJSONObject(i).getString(
						"description"));
			}
		} catch (JSONException e) {
			Log.e(AppConstants.DEBUG_TAG, "Cannot process JSON results", e);
		}
		return resultList;
	}
