/*
2013 Measuring Broadband America Program
Mobile Measurement Android Application
Copyright (C) 2012  SamKnows Ltd.

The FCC Measuring Broadband America (MBA) Program's Mobile Measurement Effort developed in cooperation with SamKnows Ltd. and diverse stakeholders employs an client-server based anonymized data collection approach to gather broadband performance data in an open and transparent manner with the highest commitment to protecting participants privacy.  All data collected is thoroughly analyzed and processed prior to public release to ensure that subscribers’ privacy interests are protected.

Data related to the radio characteristics of the handset, information about the handset type and operating system (OS) version, the GPS coordinates available from the handset at the time each test is run, the date and time of the observation, and the results of active test results are recorded on the handset in JSON(JavaScript Object Notation) nested data elements within flat files.  These JSON files are then transmitted to storage servers at periodic intervals after the completion of active test measurements.

This Android application source code is made available under the GNU GPL2 for testing purposes only and intended for participants in the SamKnows/FCC Measuring Broadband American program.  It is not intended for general release and this repository may be disabled at any time.


This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/


package com.samknows.measurement.statemachine.state;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

import com.samknows.measurement.AppSettings;
import com.samknows.measurement.Logger;
import com.samknows.measurement.MainService;
import com.samknows.measurement.environment.PhoneIdentityDataCollector;
import com.samknows.measurement.net.AssociateAction;
import com.samknows.measurement.statemachine.StateResponseCode;
import com.samknows.measurement.util.OtherUtils;

public class AssociateState extends BaseState{

	public AssociateState(MainService c) {
		super(c);
	}

	@Override
	public StateResponseCode executeState() {
		if (!OtherUtils.isPhoneAssosiated(ctx)) {
			String unitId = AppSettings.getInstance().getUnitId();
			String imei = new PhoneIdentityDataCollector(ctx).collect().imei;
			AssociateAction action = new AssociateAction(unitId, imei); 
			action.execute();
			return action.isSuccess() ? StateResponseCode.OK : StateResponseCode.FAIL;
		} else {
			Logger.d(this, "already assosiated...skiping");
			return StateResponseCode.OK;
		}
	}

}