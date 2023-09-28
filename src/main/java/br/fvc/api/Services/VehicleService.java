package br.fvc.api.Services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.fvc.api.Domain.Generic.GenericRequestDTO;
import br.fvc.api.Domain.Generic.GenericResponseDTO;
import br.fvc.api.Domain.Vehicle.HomeResponseDTO;
import br.fvc.api.Domain.Vehicle.VehicleRequestDTO;
import br.fvc.api.Domain.Vehicle.VehicleResponseDTO;
import br.fvc.api.Models.Agency;
import br.fvc.api.Models.Model;
import br.fvc.api.Models.Vehicle;
import br.fvc.api.Repositories.AgencyRepository;
import br.fvc.api.Repositories.ModelRepository;
import br.fvc.api.Repositories.VehicleRepository;

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
                return ResponseEntity.status(400).body(new GenericResponseDTO(true, "Placa j� cadastrada"));
            }

            Vehicle vehicle = new Vehicle(vehicleDTO);

            vehicleRepository.save(vehicle);

            this.changeAmount(vehicle.getModelo().getId(), "insert");

            return ResponseEntity.status(201).body(new GenericResponseDTO(false, "Veiculo cadastrado com sucesso!"));

        } catch (Exception e) {
            return ResponseEntity.status(400).body(new GenericResponseDTO(true, e.getMessage()));
        }
    }

    public ResponseEntity<Object> filter(GenericRequestDTO data) {
        List<VehicleResponseDTO> vehicle = this.vehicleRepository.findByModeloOrMarca(data.modelOrMarca).stream()
                .map(VehicleResponseDTO::new).toList();

        if (vehicle.isEmpty()) {
            return ResponseEntity.status(404).body(new GenericResponseDTO(true, "Veiculo não encontrado!"));
        }

        return ResponseEntity.status(200).body(vehicle);
    }

    public ResponseEntity<Object> update(Long id, VehicleRequestDTO vehicleDTO) {
        try {

            Vehicle vehicle = vehicleRepository.findById(id).get();

            if (!vehicleDTO.placa.equals(vehicle.getPlaca()) &&
                    vehicleRepository.existsByPlaca(vehicleDTO.placa)) {
                return ResponseEntity.status(400).body(new GenericResponseDTO(true, "Placa j� existe"));
            }

            if (vehicleDTO.modelo.getId() != vehicle.getModelo().getId()) {
                this.changeAmount(vehicle.getModelo().getId(), "delete");
                this.changeAmount(vehicleDTO.modelo.getId(), "insert");
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
                        .body(new GenericResponseDTO(true, "Nenhum veiculo disponível nessa agência!"));
            }

            return ResponseEntity.status(200).body(vehicles);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

}
