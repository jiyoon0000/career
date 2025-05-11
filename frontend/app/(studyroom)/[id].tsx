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
} from 'react-native';
import { getStudyRoomById } from '@/api/StudyRoom';
import type { StudyRoomDetail } from '@/types/studyroom';

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

  if (loading) return <ActivityIndicator style={{ flex: 1 }} />;
  if (!room) return <Text style={{ padding: 20 }}>스터디룸 정보를 불러올 수 없습니다.</Text>;

  return (
    <ScrollView contentContainerStyle={styles.container}>
      {/* 뒤로가기 버튼 */}
      <TouchableOpacity onPress={router.back} style={styles.backButton}>
        <Image source={require('@/assets/images/item-actionbutton-navigation-bar-left.png')} style={styles.backIcon} />
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
    </ScrollView>
  );
}

const styles = StyleSheet.create({
  container: {
    padding: 20,
    paddingTop: 60, // back button 공간 확보
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
});
