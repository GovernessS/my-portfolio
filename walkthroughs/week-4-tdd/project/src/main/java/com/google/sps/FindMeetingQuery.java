// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps;

import java.util.List;
import java.util.Set;
import java.util.ArrayList; 
import java.util.Arrays;
import java.util.HashSet;
import java.util.Collection;
import java.util.Collections;   

public final class FindMeetingQuery {
    public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {
        ArrayList<TimeRange> openSlots = new ArrayList<TimeRange>();
        ArrayList<TimeRange> filledTime = new ArrayList<TimeRange>();
        Set<String> Attendees = new HashSet<String>(request.getAttendees());

        for(Event event: events){
            Set<String> eventAttendees = new HashSet<String>(event.getAttendees());
            eventAttendees.retainAll(Attendees);
            
            if (!eventAttendees.isEmpty()) {
                filledTime.add(event.getWhen());
            }
        }

        ArrayList<TimeRange> allEventTimes = returnEventTime(filledTime);

        int endTime = TimeRange.START_OF_DAY;
        long duration = request.getDuration();

        for(TimeRange curEvent: allEventTimes){
            if (curEvent.start() - endTime >= duration){
                openSlots.add(TimeRange.fromStartEnd(endTime, curEvent.start(), false));
            }
            endTime = curEvent.end();
        }

        if (TimeRange.END_OF_DAY - endTime >= duration) {
            openSlots.add(TimeRange.fromStartEnd(endTime, TimeRange.END_OF_DAY, true));
        }

        return openSlots;
    }

    private ArrayList<TimeRange> returnEventTime(ArrayList<TimeRange> eventTime){
        if (eventTime.size() < 1) {
            return eventTime;
        }

        Collections.sort(eventTime, TimeRange.ORDER_BY_START);

        ArrayList<TimeRange> allEventTimes = new ArrayList();  
        int startTime = eventTime.get(0).start();
        int endTime = eventTime.get(0).end();

        for (int curIndex = 0; curIndex < eventTime.size(); curIndex++){
            TimeRange curEvent = eventTime.get(curIndex);

            if (TimeRange.fromStartEnd(startTime, endTime, false).overlaps(curEvent)) {
                endTime = Math.max(endTime, curEvent.end());
            }

            else {
                allEventTimes.add(TimeRange.fromStartEnd(startTime, endTime, false));
                startTime = curEvent.start();
                endTime = curEvent.end();
            }
        }

        allEventTimes.add(TimeRange.fromStartEnd(startTime, endTime, false));
        
        return allEventTimes;
    }
}
