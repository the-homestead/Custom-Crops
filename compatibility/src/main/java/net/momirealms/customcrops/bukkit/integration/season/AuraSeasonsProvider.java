/*
 *  Copyright (C) <2024> <XiaoMoMi>
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package net.momirealms.customcrops.bukkit.integration.season;

import com.artyfw.aura.seasons.api.AuraSeasonsAPI;

import net.momirealms.customcrops.api.core.world.Season;
import net.momirealms.customcrops.api.integration.SeasonProvider;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.jetbrains.annotations.NotNull;

public class AuraSeasonsProvider implements SeasonProvider {

    private final AuraSeasonsAPI api;

    public AuraSeasonsProvider() {
        RegisteredServiceProvider<AuraSeasonsAPI> registration =
                Bukkit.getServicesManager().getRegistration(AuraSeasonsAPI.class);

        if (registration == null) {
            throw new IllegalStateException("AuraSeasons API is not registered");
        }

        this.api = registration.getProvider();
    }

    @NotNull
    @Override
    public Season getSeason(@NotNull World world) {
        com.artyfw.aura.seasons.Season season = api.getSeason(world);

        if (season == null) {
            return Season.DISABLE;
        }

        return switch (season) {
            case WINTER -> Season.WINTER;
            case SPRING -> Season.SPRING;
            case SUMMER -> Season.SUMMER;
            case AUTUMN -> Season.AUTUMN;
        };
    }

    @Override
    public String identifier() {
        return "AuraSeasons";
    }
}