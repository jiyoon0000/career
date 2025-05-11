import { Tabs } from 'expo-router';
import React from 'react';
import { Image, Platform } from 'react-native';

import { HapticTab } from '@/components/HapticTab';
import TabBarBackground from '@/components/ui/TabBarBackground';
import { Colors } from '@/constants/Colors';
import { useColorScheme } from '@/hooks/useColorScheme';

export default function TabLayout() {
  const colorScheme = useColorScheme();

  return (
    <Tabs
      screenOptions={{
        tabBarActiveTintColor: '#2379FA',
        headerShown: false,
        tabBarButton: HapticTab,
        tabBarBackground: TabBarBackground,
        tabBarStyle: Platform.select({
          ios: {
            position: 'absolute',
          },
          default: {},
        }),
      }}>
      <Tabs.Screen
        name="daily/index"
        options={{
          title: '데일리',
          tabBarIcon: ({ focused }) => (
            <Image
              source={require('@/assets/images/icon-daily-28.png')}
              style={{ width: 28, height: 28, tintColor: focused ? '#2379FA' : '#999' }}
            />
          ),
        }}
      />
      <Tabs.Screen
        name="career/index"
        options={{
          title: '커리어셋',
          tabBarIcon: ({ focused }) => (
            <Image
              source={require('@/assets/images/icon-careerset-28.png')}
              style={{ width: 28, height: 28, tintColor: focused ? '#2379FA' : '#999' }}
            />
          ),
        }}
      />
      <Tabs.Screen
        name="studyroom/index"
        options={{
          title: '스터디룸',
          tabBarIcon: ({ focused }) => (
            <Image
              source={require('@/assets/images/icon-studyroom-active-28.png')}
              style={{ width: 28, height: 28, tintColor: focused ? '#2379FA' : '#999' }}
            />
          ),
        }}
      />
      <Tabs.Screen
        name="my/index"
        options={{
          title: '마이',
          tabBarIcon: ({ focused }) => (
            <Image
              source={require('@/assets/images/icon-my-28.png')}
              style={{ width: 28, height: 28, tintColor: focused ? '#2379FA' : '#999' }}
            />
          ),
        }}
      />
    </Tabs>
  );
}