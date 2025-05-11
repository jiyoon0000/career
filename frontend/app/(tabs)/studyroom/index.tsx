import React, { useEffect, useState } from 'react';
import {
  SafeAreaView,
  ScrollView,
  Text,
  View,
  Image,
  TouchableOpacity,
  FlatList,
  StyleSheet,
  Alert,
} from 'react-native';
import { fetchStudyRooms } from '@/api/StudyRoom';

type StudyRoom = {
  id: number;
  name: string;
  region: string;
  payType: string;
  category: string;
  imageUrl: string;
};

export default function StudyRoomScreen() {
  const [studyRooms, setStudyRooms] = useState<StudyRoom[]>([]);

  useEffect(() => {
    (async () => {
      try {
        const result = await fetchStudyRooms();
        setStudyRooms(result);
      } catch (e) {
        Alert.alert('스터디룸 불러오기 실패', '데이터를 불러오지 못했습니다.');
      }
    })();
  }, []);

  if (studyRooms.length === 0) {
    return (
      <SafeAreaView style={styles.emptyContainer}>
        <View style={styles.header}>
          <Text style={styles.title}>스터디룸</Text>
          <TouchableOpacity style={styles.filterButton} onPress={() => Alert.alert('필터 기능은 준비 중입니다')}>
            <Text style={styles.filterText}>필터</Text>
            <Image source={require('@/assets/images/button-icon.png')} style={styles.filterIcon} />
          </TouchableOpacity>
        </View>
        <View style={styles.emptyContent}>
          <Text style={styles.emptyText}>조건에 맞는 스터디룸이 없어요.</Text>
          <Text style={styles.emptySubText}>적합한 필터를 넣어보세요.</Text>
          <TouchableOpacity style={styles.blueButton}>
            <Text style={styles.blueButtonText}>의견 남기기</Text>
          </TouchableOpacity>
        </View>
      </SafeAreaView>
    );
  }

  return (
    <SafeAreaView style={styles.container}>
      <View style={styles.header}>
        <Text style={styles.title}>스터디룸</Text>
        <TouchableOpacity style={styles.filterButton} onPress={() => Alert.alert('필터 기능은 준비 중입니다')}>
          <Text style={styles.filterText}>필터</Text>
          <Image source={require('@/assets/images/button-icon.png')} style={styles.filterIcon} />
        </TouchableOpacity>
      </View>

      <FlatList
        contentContainerStyle={styles.list}
        data={studyRooms}
        keyExtractor={(item) => item.id.toString()}
        renderItem={({ item }) => (
          <View style={styles.card}>
            <Image source={{ uri: item.imageUrl }} style={styles.cardImage} />
            <View style={styles.cardContent}>
              <Text style={styles.category}>{item.category}</Text>
              <Text style={styles.cardTitle}>{item.name}</Text>
              <View style={styles.tagContainer}>
                <Text style={styles.tag}>{item.region}</Text>
                <Text style={[styles.tag, item.payType === '무료' && styles.freeTag]}>{item.payType}</Text>
              </View>
            </View>
          </View>
        )}
      />
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  container: { flex: 1, backgroundColor: '#FFFFFF' },
  emptyContainer: { flex: 1, backgroundColor: '#FFFFFF' },
  emptyContent: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
  emptyText: {
    fontSize: 16,
    fontWeight: 'bold',
    color: '#111111',
    marginBottom: 8,
  },
  emptySubText: {
    fontSize: 14,
    color: '#767676',
    marginBottom: 24,
  },
  blueButton: {
    backgroundColor: '#2379FA',
    paddingVertical: 12,
    paddingHorizontal: 24,
    borderRadius: 8,
  },
  blueButtonText: {
    color: '#FFFFFF',
    fontWeight: 'bold',
    fontSize: 14,
  },
  header: {
    paddingTop: 44,
    paddingBottom: 12,
    paddingHorizontal: 20,
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
  },
  title: { fontSize: 20, fontWeight: 'bold', color: '#111111' },
  filterButton: {
    flexDirection: 'row',
    borderColor: '#E5E5EC',
    borderWidth: 1,
    borderRadius: 100,
    paddingVertical: 6,
    paddingHorizontal: 12,
    alignItems: 'center',
  },
  filterText: { fontSize: 13, color: '#505050', marginRight: 4 },
  filterIcon: { width: 16, height: 16 },
  list: { paddingHorizontal: 20 },
  card: {
    flexDirection: 'row',
    marginBottom: 20,
    backgroundColor: '#FFFFFF',
  },
  cardImage: { width: 100, height: 100, borderRadius: 8, marginRight: 12 },
  cardContent: { flex: 1, justifyContent: 'center' },
  category: { fontSize: 12, color: '#767676' },
  cardTitle: { fontSize: 14, fontWeight: 'bold', color: '#111111', marginBottom: 4 },
  tagContainer: { flexDirection: 'row', gap: 6 },
  tag: {
    backgroundColor: '#F7F7FB',
    borderRadius: 4,
    paddingVertical: 2,
    paddingHorizontal: 6,
    fontSize: 11,
    color: '#505050',
    fontWeight: 'bold',
  },
  freeTag: {
    backgroundColor: '#EBF3FF',
    color: '#2379FA',
  },
});