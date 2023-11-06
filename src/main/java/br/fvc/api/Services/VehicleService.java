package br.fvc.api.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.fvc.api.dtos.generic.GenericRequestDTO;
import br.fvc.api.dtos.generic.GenericResponseDTO;
import br.fvc.api.dtos.vehicle.HomeResponseDTO;
import br.fvc.api.dtos.vehicle.VehicleRequestDTO;
import br.fvc.api.dtos.vehicle.VehicleResponseDTO;
import br.fvc.api.models.Agency;
import br.fvc.api.models.Model;
import br.fvc.api.models.Vehicle;
import br.fvc.api.repositories.AgencyRepository;
import br.fvc.api.repositories.ModelRepository;
import br.fvc.api.repositories.VehicleRepository;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private ModelRepository modelRepository;

    @Autowired
    private AgencyRepository agencyRepository;

    public ResponseEntity<Object> findAll() {
        HashMap<String, Object> objects = new HashMap<String, Object>();

        List<Model> models = modelRepository.findAllModelOrderByIdDesc();

        List<Agency> agencies = agencyRepository.findAllAgencyOrderByIdDesc();

        List<VehicleResponseDTO> vehicles = vehicleRepository.findAllVehicleOrderByIdDesc().stream()
                .map(VehicleResponseDTO::new).toList();

        objects.put("veiculo", vehicles);
        objects.put("modelo", models);
        objects.put("agencia", agencies);

        return ResponseEntity.status(200).body(objects);
    }

    public ResponseEntity<Object> index(Long id) {
        List<VehicleResponseDTO> vehicles = vehicleRepository.findById(id).stream()
                .map(VehicleResponseDTO::new).toList();
        return ResponseEntity.status(200).body(vehicles);
    }

    public ResponseEntity<Object> store(VehicleRequestDTO vehicleDTO) {
        try {

            if (vehicleRepository.existsByPlaca(vehicleDTO.placa)) {
                return ResponseEntity.status(400).body(new GenericResponseDTO(true, "Placa já cadastrada"));
            }

            Vehicle vehicle = new Vehicle(vehicleDTO);

            Agency agency = agencyRepository.findById(vehicleDTO.agencia.getId()).get();

            agency.setQuantidade_total(agency.getQuantidade_total() + 1);

            agencyRepository.save(agency);

            vehicleRepository.save(vehicle);

            this.changeAmount(vehicle.getModelo().getId(), "insert");

            return ResponseEntity.status(201).body(new GenericResponseDTO(false, "Veiculo cadastrado com sucesso!"));

        } catch (Exception e) {
            return ResponseEntity.status(400).body(new GenericResponseDTO(true, e.getMessage()));
        }
    }

    public ResponseEntity<Object> filter(GenericRequestDTO data) {
        List<VehicleResponseDTO> vehicle = this.vehicleRepository.findByModeloOrMarcaOrPlaca(data.modelOrMarcaOrPlaca)
                .stream()
                .map(VehicleResponseDTO::new).toList();

        if (vehicle.isEmpty()) {
            return ResponseEntity.status(404).body(new GenericResponseDTO(true, "Veiculo não encontrado!"));
        }

        return ResponseEntity.status(200).body(vehicle);
    }

    public ResponseEntity<Object> update(Long id, VehicleRequestDTO vehicleDTO) {
        try {

            Vehicle vehicle = vehicleRepository.findById(id).get();

            Agency agency = agencyRepository.findById(vehicleDTO.agencia.getId()).get();

            if (!vehicleDTO.placa.equals(vehicle.getPlaca()) &&
                    vehicleRepository.existsByPlaca(vehicleDTO.placa)) {
                return ResponseEntity.status(400).body(new GenericResponseDTO(true, "Placa já existe!"));
            }

            if (vehicleDTO.modelo.getId() != vehicle.getModelo().getId()) {
                this.changeAmount(vehicle.getModelo().getId(), "delete");
                this.changeAmount(vehicleDTO.modelo.getId(), "insert");
            }

            if (!vehicle.getAgencia().getId().equals(agency.getId())) {
                vehicle.getAgencia().setQuantidade_total(vehicle.getAgencia().getQuantidade_total() - 1);
                agency.setQuantidade_total(agency.getQuantidade_total() + 1);

                agencyRepository.save(agency);
            }

            vehicle.setAno(vehicleDTO.ano);
            vehicle.setCapacidade(vehicleDTO.capacidade);
            vehicle.setCor(vehicleDTO.cor.toUpperCase());
            vehicle.setMarca(vehicleDTO.marca.toUpperCase());
            vehicle.setModelo(vehicleDTO.modelo);
            vehicle.setPlaca(vehicleDTO.placa.toUpperCase());
            vehicle.setAgencia(vehicleDTO.agencia);

            vehicleRepository.save(vehicle);

            return ResponseEntity.status(200).body(new GenericResponseDTO(false, "Veiculo atualizado com sucesso!"));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new GenericResponseDTO(true, e.getMessage()));
        }
    }

    public ResponseEntity<Object> delete(Long id) {
        try {

            Vehicle vehicle = vehicleRepository.findById(id).get();

            this.changeAmount(vehicle.getModelo().getId(), "delete");

            vehicleRepository.deleteById(id);
            return ResponseEntity.status(200).body(new GenericResponseDTO(false, "Veiculo excluido com sucesso"));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new GenericResponseDTO(true, e.getMessage()));
        }
    }

    public void changeAmount(Long id, String insert) {
        Model model = modelRepository.findById(id).get();

        model.setQuantidade(insert.equals("insert") ? model.getQuantidade() + 1 : model.getQuantidade() - 1);

        modelRepository.save(model);
    }

    public ResponseEntity<Object> home(VehicleRequestDTO data) {
        try {

            SimpleDateFormat convertDate = new SimpleDateFormat("yyyy-MM-dd");
            Date startDateConvert = convertDate.parse(data.startDate);
            Date endDateConvert = convertDate.parse(data.endDate);
            java.sql.Date convertStartDateSql = new java.sql.Date(startDateConvert.getTime());
            java.sql.Date convertEndDateSql = new java.sql.Date(endDateConvert.getTime());

            List<HomeResponseDTO> vehicles = this.vehicleRepository.findByListVehicles(convertStartDateSql,
                    convertEndDateSql,
                    data.agencia.getId());

            if (vehicles.isEmpty()) {
                return ResponseEntity.status(400)
                        .body(new GenericResponseDTO(true, "Nenhum veículo disponível nessa data para essa agência!"));
            }

            return ResponseEntity.status(200).body(vehicles);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

}
