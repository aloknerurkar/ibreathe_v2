package com.ibreathe.ibreathe_v2.global;


import com.ibreathe.ibreathe_v2.R;

public class GlobalVariable {
	private static GlobalVariable instance;

	private final static String SERVER_IP = "10.0.2.2";
	private final static String PORT = "8080";
	private final static String TEST_PATH = "postUser/";
	private final static String LOCATION_PATH = "postLocation/";
    private final static String GET_ALL_EVENTS = "https://ibreathe.cfapps.io/event/";
    private final static String GET_EVENTS_LOC = "http://server.ibreatheinc.com/events/1";
    private final static String CREATE_EVENT = "http://server.ibreatheinc.com/event_create/1";
    private final static String REGISTER = "http://server.ibreatheinc.com/register/1";
    private final static String GET_MY_EVENTS = "http://server.ibreatheinc.com/my_events/1";
    private final static String GET_EVENTS_FILTERS = "";

	public static synchronized GlobalVariable getInstance() {
		if (instance == null) {
			instance = new GlobalVariable();
		}
		return instance;
	}

	public String getSERVER_IP() {
		return SERVER_IP;
	}
    public String getLoc_IP() {
        return GET_EVENTS_LOC;
    }
    public String getCreateEvent() {
        return CREATE_EVENT;
    }
    public String getGetMyEvents() {
        return GET_MY_EVENTS;
    }
    public String getRegisterUser() {
        return REGISTER;
    }

	public String getPORT() {
		return PORT;
	}

	public String getAuthURL() {
		return String.format("http://%s:%s/CentralServer/json/", getSERVER_IP(), getPORT());
	}

	public String getTestPath() {
		return GET_ALL_EVENTS;
	}

	public String getLocationURL() {
		return LOCATION_PATH;
	}

    public int getIcon(long filter) {
        if (filter == 1) {
            return R.drawable.soccer;
        }

        if (filter == 2) {
            return R.drawable.basketball;
        }

        if (filter == 3) {
            return R.drawable.tennis;
        }

        if (filter == 4) {
            return R.drawable.tennis;
        }

        return R.drawable.soccer;
    }
}
