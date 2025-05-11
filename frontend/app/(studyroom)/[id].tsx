import { useLocalSearchParams, useRouter } from 'expo-router';
import React, { useEffect, useState } from 'react';
import {
  View,
  Text,
  ScrollView,
  ActivityIndicator,
  Image,
  StyleSheet,
  TouchableOpacity,
  Alert,
  Platform,
} from 'react-native';
import * as Linking from 'expo-linking';
import { WebView } from 'react-native-webview';
import { getStudyRoomById } from '@/api/StudyRoom';
import type { StudyRoomDetail } from '@/types/studyroom';

const KAKAO_JS_KEY = process.env.EXPO_PUBLIC_KAKAO_JS_KEY;

export default function StudyRoomDetailScreen() {
  const { id } = useLocalSearchParams();
  const router = useRouter();
  const [room, setRoom] = useState<StudyRoomDetail | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (!id) return;
    getStudyRoomById(Number(id))
      .then(setRoom)
      .catch((err) => console.error('상세 정보 불러오기 실패', err))
      .finally(() => setLoading(false));
  }, [id]);

  const KAKAO_MAP_HTML = (x: number, y: number, name: string) => `
    <!DOCTYPE html>
    <html>
    <head>
      <meta charset="utf-8" />
      <meta name="viewport" content="width=device-width, initial-scale=1.0" />
      <style> html, body, #map { margin:0; padding:0; height:100%; } </style>
      <script src="https://dapi.kakao.com/v2/maps/sdk.js?appkey=${KAKAO_JS_KEY}"></script>
    </head>
    <body>
      <div id="map"></div>
      <script>
        var mapContainer = document.getElementById('map');
        var mapOption = {
          center: new kakao.maps.LatLng(${y}, ${x}),
          level: 3
        };
        var map = new kakao.maps.Map(mapContainer, mapOption);
        var marker = new kakao.maps.Marker({
          position: new kakao.maps.LatLng(${y}, ${x})
        });
        marker.setMap(map);
        var infowindow = new kakao.maps.InfoWindow({
          content: '<div style="padding:6px 12px;font-size:14px;">${name}</div>'
        });
        infowindow.open(map, marker);
      </script>
    </body>
    </html>
  `;

  if (loading) return <ActivityIndicator style={{ flex: 1 }} />;
  if (!room) return <Text style={{ padding: 20 }}>스터디룸 정보를 불러올 수 없습니다.</Text>;

  return (
    <ScrollView contentContainerStyle={styles.container}>
      <TouchableOpacity onPress={router.back} style={styles.backButton}>
        <Image
          source={require('@/assets/images/item-actionbutton-navigation-bar-left.png')}
          style={styles.backIcon}
        />
      </TouchableOpacity>

      <Text style={styles.name}>{room.name}</Text>
      <Text style={styles.category}>{room.category}</Text>
      <Image source={{ uri: room.imageUrl }} style={styles.image} />

      <View style={styles.tags}>
        <Text style={styles.tag}>{room.region}</Text>
        <Text style={[styles.tag, room.payType === '무료' && styles.freeTag]}>
          {room.payType}
        </Text>
      </View>

      <Text style={styles.sectionTitle}>시설 소개</Text>
      <Text style={styles.description}>{room.intro || '시설 소개 정보 없음'}</Text>

      <Text style={styles.sectionTitle}>운영 정보</Text>
      <Text style={styles.info}>운영 시간: {room.openingHours || '정보 없음'}</Text>
      <Text style={styles.info}>주소: {room.address || '정보 없음'}</Text>
      <Text style={styles.info}>문의처: {room.contact || '정보 없음'}</Text>

      <Text style={styles.sectionTitle}>위치 보기</Text>
      <View style={styles.webviewContainer}>
        {Platform.OS === 'web' ? (
          <iframe
            srcDoc={KAKAO_MAP_HTML(room.x, room.y, room.name)}
            width="100%"
            height="300"
            style={{ border: 'none' }}
          />
        ) : (
          <WebView
            originWhitelist={['*']}
            source={{ html: KAKAO_MAP_HTML(room.x, room.y, room.name) }}
            style={{ height: 300 }}
          />
        )}
      </View>

      <TouchableOpacity
        style={styles.reserveButton}
        onPress={() => {
          if (room.serviceUrl) {
            Linking.openURL(room.serviceUrl);
          } else {
            Alert.alert('예약 링크가 없습니다');
          }
        }}
      >
        <Text style={styles.reserveButtonText}>예약하러 가기</Text>
      </TouchableOpacity>
    </ScrollView>
  );
}

const styles = StyleSheet.create({
  container: {
    padding: 20,
    paddingTop: 60,
    backgroundColor: '#fff',
  },
  backButton: {
    position: 'absolute',
    top: 20,
    left: 20,
    zIndex: 10,
    padding: 8,
  },
  backIcon: {
    width: 24,
    height: 24,
  },
  name: {
    fontSize: 24,
    fontWeight: 'bold',
    marginBottom: 4,
  },
  category: {
    fontSize: 14,
    color: '#767676',
    marginBottom: 8,
  },
  image: {
    width: '100%',
    height: 180,
    borderRadius: 8,
    marginBottom: 16,
  },
  tags: {
    flexDirection: 'row',
    gap: 6,
    marginBottom: 12,
  },
  tag: {
    backgroundColor: '#F0F0F0',
    paddingHorizontal: 8,
    paddingVertical: 4,
    borderRadius: 6,
    fontSize: 12,
    color: '#505050',
    fontWeight: 'bold',
  },
  freeTag: {
    backgroundColor: '#EBF3FF',
    color: '#2379FA',
  },
  sectionTitle: {
    fontSize: 16,
    fontWeight: 'bold',
    marginTop: 20,
    marginBottom: 6,
  },
  description: {
    fontSize: 14,
    lineHeight: 20,
  },
  info: {
    fontSize: 14,
    marginBottom: 4,
  },
  webviewContainer: {
    borderRadius: 8,
    overflow: 'hidden',
    marginTop: 8,
    marginBottom: 20,
  },
  reserveButton: {
    marginTop: 20,
    backgroundColor: '#2379FA',
    paddingVertical: 14,
    borderRadius: 8,
    alignItems: 'center',
  },
  reserveButtonText: {
    color: '#FFFFFF',
    fontSize: 16,
    fontWeight: 'bold',
  },
});
