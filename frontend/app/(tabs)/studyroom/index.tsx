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
  const [previewCount, setPreviewCount] = useState<number | null>(null);

  const fetchRooms = async () => {
    try {
      const result = await fetchFilteredStudyRooms(selectedRegion, selectedPayType);
      setStudyRooms(result);
    } catch (e) {
      Alert.alert('스터디룸 불러오기 실패', '데이터를 불러오지 못했습니다.');
    }
  };

  const previewRooms = async (region: string, pay: string) => {
    try {
      const result = await fetchFilteredStudyRooms(region, pay);
      setPreviewCount(result.length);
    } catch {
      setPreviewCount(null);
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

  const handleReset = () => {
    setSelectedRegion('');
    setSelectedPayType('');
    setPreviewCount(null);
  };

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
        backdropOpacity={0.5}
        animationIn="slideInUp"
        animationOut="slideOutDown"
        swipeDirection="down"
        onSwipeComplete={toggleModal}
      >
        <View style={styles.modal}>
          <View style={styles.modalHeaderContainer}>
            <Text style={styles.modalHeaderTitle}>필터</Text>
            <TouchableOpacity onPress={toggleModal} style={styles.closeButton}>
              <Text style={styles.closeButtonText}>✕</Text>
            </TouchableOpacity>
          </View>

          <View style={styles.divider} />

          <Text style={styles.sectionTitle}>요금</Text>
          <View style={styles.payTypeContainer}>
            <TouchableOpacity
              style={[
                styles.payTypeButton,
                selectedPayType === '' && styles.payTypeButtonSelected
              ]}
              onPress={() => {
                setSelectedPayType('');
                previewRooms(selectedRegion, '');
              }}
            >
              <Text
                style={[
                  styles.payTypeText,
                  selectedPayType === '' && styles.payTypeTextSelected
                ]}
              >
                전체
              </Text>
            </TouchableOpacity>
            {payTypes.map((type) => (
              <TouchableOpacity
                key={type}
                style={[
                  styles.payTypeButton,
                  selectedPayType === type && styles.payTypeButtonSelected
                ]}
                onPress={() => {
                  const next = type === selectedPayType ? '' : type;
                  setSelectedPayType(next);
                  previewRooms(selectedRegion, next);
                }}
              >
                <Text
                  style={[
                    styles.payTypeText,
                    selectedPayType === type && styles.payTypeTextSelected
                  ]}
                >
                  {type}
                </Text>
              </TouchableOpacity>
            ))}
          </View>

          <Text style={styles.sectionTitle}>자치구</Text>
          <View style={styles.regionContainer}>
            {regions.map((region) => (
              <TouchableOpacity 
                key={region}
                style={styles.regionItemContainer}
                onPress={() => {
                  const next = region === selectedRegion ? '' : region;
                  setSelectedRegion(next);
                  previewRooms(next, selectedPayType);
                }}
              >
                <View style={[
                  styles.checkbox,
                  selectedRegion === region && styles.checkboxSelected
                ]}>
                  {selectedRegion === region && (
                    <Text style={styles.checkmark}>✓</Text>
                  )}
                </View>
                <Text style={styles.regionText}>{region}</Text>
              </TouchableOpacity>
            ))}
          </View>

          {/* Footer Buttons */}
          <View style={styles.modalFooter}>
            <TouchableOpacity 
              style={styles.resetButtonContainer}
              onPress={handleReset}
            >
              <Image 
                source={require('@/assets/images/button-icon-reset.png')} 
                style={styles.resetIcon} 
              />
              <Text style={styles.resetButtonText}>초기화</Text>
            </TouchableOpacity>
            
            <TouchableOpacity 
              style={styles.applyButton} 
              onPress={toggleModal}
            >
              <Text style={styles.applyButtonText}>
                {previewCount !== null 
                  ? `${previewCount}개 결과보기` 
                  : `${studyRooms.length}개 결과보기`}
              </Text>
            </TouchableOpacity>
          </View>
        </View>
      </Modal>
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  container: { 
    flex: 1, 
    backgroundColor: '#FFFFFF' 
  },
  header: { 
    paddingTop: 44, 
    paddingHorizontal: 20 
  },
  subHeader: { 
    marginTop: 12, 
    marginBottom: 12, 
    flexDirection: 'row', 
    justifyContent: 'space-between', 
    alignItems: 'center' 
  },
  title: { 
    fontSize: 20, 
    fontWeight: 'bold', 
    color: '#111111' 
  },
  totalText: { 
    fontSize: 14, 
    color: '#111111' 
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
  filterText: { 
    fontSize: 13, 
    color: '#505050', 
    marginRight: 4 
  },
  filterIcon: { 
    width: 16, 
    height: 16 
  },
  emptyContent: { 
    flex: 1, 
    justifyContent: 'center', 
    alignItems: 'center' 
  },
  emptyText: { 
    fontSize: 16, 
    fontWeight: 'bold', 
    color: '#111111', 
    marginBottom: 8 
  },
  emptySubText: { 
    fontSize: 14, 
    color: '#767676', 
    marginBottom: 24 
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
    fontSize: 14 
  },
  list: { 
    paddingHorizontal: 20 
  },
  card: { 
    flexDirection: 'row', 
    marginBottom: 20, 
    backgroundColor: '#FFFFFF' 
  },
  cardImage: { 
    width: 100, 
    height: 100, 
    borderRadius: 8, 
    marginRight: 12 
  },
  cardContent: { 
    flex: 1, 
    justifyContent: 'center' 
  },
  category: { 
    fontSize: 12, 
    color: '#767676' 
  },
  cardTitle: { 
    fontSize: 14, 
    fontWeight: 'bold', 
    color: '#111111', 
    marginBottom: 4 
  },
  tagContainer: { 
    flexDirection: 'row', 
    gap: 6 
  },
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
    color: '#2379FA' 
  },
  
  modal: { 
    backgroundColor: '#FFFFFF', 
    borderTopLeftRadius: 20, 
    borderTopRightRadius: 20, 
    paddingTop: 16, 
    paddingBottom: 24, 
    maxHeight: '80%' 
  },
  modalHeaderContainer: {
    flexDirection: 'row',
    justifyContent: 'center',
    alignItems: 'center',
    paddingHorizontal: 16,
    paddingBottom: 16,
    position: 'relative',
  },
  modalHeaderTitle: {
    fontSize: 18,
    fontWeight: 'bold',
    color: '#000000',
  },
  closeButton: {
    position: 'absolute',
    right: 16,
    padding: 8,
  },
  closeButtonText: {
    fontSize: 18,
    color: '#000000',
    fontWeight: '300',
  },
  divider: {
    height: 1,
    backgroundColor: '#EEEEEE',
    marginBottom: 20,
  },
  sectionTitle: {
    fontSize: 16,
    fontWeight: 'bold',
    color: '#000000',
    paddingHorizontal: 20,
    marginBottom: 12,
  },
  payTypeContainer: {
    flexDirection: 'row',
    paddingHorizontal: 20,
    marginBottom: 24,
    gap: 8,
  },
  payTypeButton: {
    flex: 1,
    height: 44,
    borderRadius: 8,
    justifyContent: 'center',
    alignItems: 'center',
    borderWidth: 1,
    borderColor: '#EEEEEE',
    backgroundColor: '#FFFFFF',
  },
  payTypeButtonSelected: {
    borderColor: '#2379FA',
    backgroundColor: '#EBF3FF',
  },
  payTypeText: {
    fontSize: 14,
    color: '#505050',
  },
  payTypeTextSelected: {
    color: '#2379FA',
    fontWeight: 'bold',
  },
  regionContainer: {
    paddingHorizontal: 20,
    flexDirection: 'row',
    flexWrap: 'wrap',
    gap: 10,
    marginBottom: 24,
  },
  regionItemContainer: {
    flexDirection: 'row',
    alignItems: 'center',
    width: '45%',
    marginBottom: 8,
  },
  checkbox: {
    width: 22,
    height: 22,
    borderRadius: 12,
    borderWidth: 1,
    borderColor: '#CCCCCC',
    justifyContent: 'center',
    alignItems: 'center',
    marginRight: 8,
    backgroundColor: '#FFFFFF',
  },
  checkboxSelected: {
    backgroundColor: '#2379FA',
    borderColor: '#2379FA',
  },
  checkmark: {
    color: '#FFFFFF',
    fontSize: 14,
    fontWeight: 'bold',
  },
  regionText: {
    fontSize: 14,
    color: '#000000',
  },
  modalFooter: {
    flexDirection: 'row',
    paddingHorizontal: 20,
    gap: 8,
    marginTop: 12,
  },
  resetButtonContainer: {
    height: 54,
    flex: 1,
    borderRadius: 8,
    borderWidth: 1,
    borderColor: '#EEEEEE',
    justifyContent: 'center',
    alignItems: 'center',
    flexDirection: 'row',
  },
  resetIcon: {
    width: 16,
    height: 16,
    marginRight: 6,
  },
  resetButtonText: {
    fontSize: 14,
    color: '#505050',
  },
  applyButton: {
    height: 54,
    flex: 1,
    backgroundColor: '#2379FA',
    borderRadius: 8,
    justifyContent: 'center',
    alignItems: 'center',
  },
  applyButtonText: {
    fontSize: 16,
    fontWeight: 'bold',
    color: '#FFFFFF',
  },
});