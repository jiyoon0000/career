import React, { useEffect, useState } from 'react';
import {
  SafeAreaView,
  View,
  Text,
  Image,
  TouchableOpacity,
  FlatList,
  StyleSheet,
  Alert,
} from 'react-native';
import Modal from 'react-native-modal';
import { fetchFilteredStudyRooms, fetchStudyRoomRegions } from '@/api/StudyRoom';

type StudyRoom = {
  id: number;
  name: string;
  region: string;
  payType: string;
  category: string;
  imageUrl: string;
};

const payTypes = ['무료', '유료'];

export default function StudyRoomScreen() {
  const [studyRooms, setStudyRooms] = useState<StudyRoom[]>([]);
  const [regions, setRegions] = useState<string[]>([]);
  const [isModalVisible, setModalVisible] = useState(false);
  const [selectedRegion, setSelectedRegion] = useState('');
  const [selectedPayType, setSelectedPayType] = useState('');

  const fetchRooms = async () => {
    try {
      const result = await fetchFilteredStudyRooms(selectedRegion, selectedPayType);
      setStudyRooms(result);
    } catch (e) {
      Alert.alert('스터디룸 불러오기 실패', '데이터를 불러오지 못했습니다.');
    }
  };

  useEffect(() => {
    fetchRooms();
  }, [selectedRegion, selectedPayType]);

  useEffect(() => {
    (async () => {
      try {
        const data = await fetchStudyRoomRegions();
        setRegions(data);
      } catch (e) {
        console.error('지역 목록 불러오기 실패', e);
      }
    })();
  }, []);

  const toggleModal = () => setModalVisible(!isModalVisible);

  return (
    <SafeAreaView style={styles.container}>
      <View style={styles.header}>
        <Text style={styles.title}>스터디룸</Text>
        <View style={styles.subHeader}>
          <Text style={styles.totalText}>
            총 <Text style={{ fontWeight: 'bold' }}>{studyRooms.length}</Text>개
          </Text>
          <TouchableOpacity style={styles.filterButton} onPress={toggleModal}>
            <Text style={styles.filterText}>필터</Text>
            <Image source={require('@/assets/images/button-icon.png')} style={styles.filterIcon} />
          </TouchableOpacity>
        </View>
      </View>

      {studyRooms.length === 0 ? (
        <View style={styles.emptyContent}>
          <Text style={styles.emptyText}>조건에 맞는 스터디룸이 없어요.</Text>
          <Text style={styles.emptySubText}>적합한 필터를 넣어보세요.</Text>
          <TouchableOpacity style={styles.blueButton}>
            <Text style={styles.blueButtonText}>의견 남기기</Text>
          </TouchableOpacity>
        </View>
      ) : (
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
      )}

      <Modal
        isVisible={isModalVisible}
        onBackdropPress={toggleModal}
        style={{ justifyContent: 'flex-end', margin: 0 }}
      >
        <View style={styles.modal}>
          <Text style={styles.modalTitle}>요금</Text>
          <View style={styles.chipGroup}>
            {payTypes.map((type) => (
              <TouchableOpacity
                key={type}
                style={[styles.chip, selectedPayType === type && styles.chipSelected]}
                onPress={() => setSelectedPayType(type === selectedPayType ? '' : type)}
              >
                <Text style={[styles.chipText, selectedPayType === type && styles.chipTextSelected]}>
                  {type}
                </Text>
              </TouchableOpacity>
            ))}
          </View>

          <Text style={styles.modalTitle}>자치구</Text>
          <View style={styles.chipGroup}>
            {regions.map((region) => (
              <TouchableOpacity
                key={region}
                style={[styles.chip, selectedRegion === region && styles.chipSelected]}
                onPress={() => setSelectedRegion(region === selectedRegion ? '' : region)}
              >
                <Text style={[styles.chipText, selectedRegion === region && styles.chipTextSelected]}>
                  {region}
                </Text>
              </TouchableOpacity>
            ))}
          </View>

          <TouchableOpacity style={styles.applyButton} onPress={toggleModal}>
            <Text style={styles.applyButtonText}>필터 적용</Text>
          </TouchableOpacity>
        </View>
      </Modal>
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  container: { flex: 1, backgroundColor: '#FFFFFF' },
  header: {
    paddingTop: 44,
    paddingHorizontal: 20,
    backgroundColor: '#FFFFFF',
  },
  subHeader: {
    marginTop: 12,
    marginBottom: 12,
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
  },
  title: { fontSize: 20, fontWeight: 'bold', color: '#111111' },
  totalText: {
    fontSize: 14,
    color: '#111111',
  },
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
  modal: {
    backgroundColor: '#FFFFFF',
    borderTopLeftRadius: 20,
    borderTopRightRadius: 20,
    padding: 20,
  },
  modalTitle: {
    fontSize: 16,
    fontWeight: 'bold',
    marginBottom: 12,
  },
  chipGroup: {
    flexDirection: 'row',
    flexWrap: 'wrap',
    gap: 10,
    marginBottom: 20,
  },
  chip: {
    paddingHorizontal: 16,
    paddingVertical: 8,
    backgroundColor: '#F0F0F0',
    borderRadius: 20,
  },
  chipSelected: {
    backgroundColor: '#2379FA',
  },
  chipText: {
    fontSize: 14,
    color: '#111111',
  },
  chipTextSelected: {
    color: '#FFFFFF',
    fontWeight: 'bold',
  },
  applyButton: {
    backgroundColor: '#2379FA',
    paddingVertical: 14,
    borderRadius: 8,
    alignItems: 'center',
  },
  applyButtonText: {
    color: '#FFFFFF',
    fontSize: 16,
    fontWeight: 'bold',
  },
});
