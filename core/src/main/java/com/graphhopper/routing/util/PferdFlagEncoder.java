/*
 *  Licensed to GraphHopper GmbH under one or more contributor
 *  license agreements. See the NOTICE file distributed with this work for
 *  additional information regarding copyright ownership.
 *
 *  GraphHopper GmbH licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except in
 *  compliance with the License. You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.graphhopper.routing.util;

import com.graphhopper.util.PMap;

/**
 * Specifies the settings for horse riding
 *
 * @author Louis Trinkle
 * @author Marvin Genz
 */
public class PferdFlagEncoder extends BikeCommonFlagEncoder {
    public PferdFlagEncoder() {
        this(4, 2, 0, false);
    }

    public PferdFlagEncoder(PMap properties) {
        this(properties.getInt("speed_bits", 4),
                properties.getInt("speed_factor", 2),
                properties.getBool("turn_costs", false) ? 1 : 0,
                properties.getBool("speed_two_directions", false));

        blockPrivate(properties.getBool("block_private", true));
        blockFords(properties.getBool("block_fords", false));
    }

    public PferdFlagEncoder(int speedBits, double speedFactor, int maxTurnCosts, boolean speedTwoDirections) {
        super(speedBits, speedFactor, maxTurnCosts, speedTwoDirections);

        avoidHighwayTags.add("trunk");
        avoidHighwayTags.add("trunk_link");
        avoidHighwayTags.add("primary");
        avoidHighwayTags.add("primary_link");
        avoidHighwayTags.add("secondary");
        avoidHighwayTags.add("secondary_link");
        // nach den Regelungen für Brandenburg sollten folgende HighwayTags vermieden werden
        avoidHighwayTags.add("cycleway");
        avoidHighwayTags.add("pedestrian");
        avoidHighwayTags.add("footway");

        // folgende HighwayTags sind für das Reiten hinzugefügt worden
        preferHighwayTags.add("bridleway");
        preferHighwayTags.add("path");
        preferHighwayTags.add("track");
        preferHighwayTags.add("service");
        preferHighwayTags.add("tertiary");
        preferHighwayTags.add("tertiary_link");
        preferHighwayTags.add("residential");
        preferHighwayTags.add("unclassified");

        // Smoothness an Reiten angepasst
        //
        // Impassable laut OSM-Wiki nur für motorisierte Fahrzeuge nicht überquerbar
        // deshalb für Pferde eine sehr langsame Geschwindigkeit
        setSmoothnessSpeedFactor(com.graphhopper.routing.ev.Smoothness.EXCELLENT, 1.0d);
        setSmoothnessSpeedFactor(com.graphhopper.routing.ev.Smoothness.GOOD, 1.0d);
        setSmoothnessSpeedFactor(com.graphhopper.routing.ev.Smoothness.INTERMEDIATE, 1.0d);
        setSmoothnessSpeedFactor(com.graphhopper.routing.ev.Smoothness.BAD, 0.9d);
        setSmoothnessSpeedFactor(com.graphhopper.routing.ev.Smoothness.VERY_BAD, 0.7d);
        setSmoothnessSpeedFactor(com.graphhopper.routing.ev.Smoothness.HORRIBLE, 0.5d);
        setSmoothnessSpeedFactor(com.graphhopper.routing.ev.Smoothness.VERY_HORRIBLE, 0.3d);
        // SmoothnessSpeed <= smoothnessFactorPushingSectionThreshold gets mapped to speed PUSHING_SECTION_SPEED
        setSmoothnessSpeedFactor(com.graphhopper.routing.ev.Smoothness.IMPASSABLE, 0.1d);

        blockByDefaultBarriers.add("kissing_gate");
        blockByDefaultBarriers.add("stile");
        blockByDefaultBarriers.add("turnstile");

        //setSpecificClassBicycle("mtb");

        // Access Values leicht angepasst
        intendedValues.add("agricultural");
        intendedValues.add("forestry");

        // Geschwindigkeiten
        setHighwaySpeed("bridleway", 18);
        setHighwaySpeed("footway", 6);
        setHighwaySpeed("platform", 6);
        setHighwaySpeed("pedestrian", 6);
        setHighwaySpeed("track", 10);
        setHighwaySpeed("service", 10);
        setHighwaySpeed("residential", 10);
        setHighwaySpeed("unclassified", 10);
        setHighwaySpeed("road", 10);
        setHighwaySpeed("trunk", 10);
        setHighwaySpeed("trunk_link", 10);
        setHighwaySpeed("primary", 10);
        setHighwaySpeed("primary_link", 10);
        setHighwaySpeed("secondary", 10);
        setHighwaySpeed("secondary_link", 10);
        setHighwaySpeed("tertiary", 10);
        setHighwaySpeed("tertiary_link", 10);

    }

    @Override
    public String getName() {
            return "reiten";
    }
}
