package com.enigma.task.trainer;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.enigma.task.trainer.dao.TrainerDao;
import com.enigma.task.trainer.dto.CommonResponse;
import com.enigma.task.trainer.dto.TrainerDto;
import com.enigma.task.trainer.exception.CustomException;
import com.enigma.task.trainer.model.Trainer;

@RestController
@RequestMapping("/trainer")
@SuppressWarnings("rawtypes")
public class TrainerController {
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private TrainerDao trainerDao;
	
	@GetMapping(value="/{trainerId}")
	public CommonResponse<TrainerDto> getById(@PathVariable("trainerId") String trainerId) throws CustomException {
		try {
			Trainer trainer = trainerDao.getById(Integer.parseInt(trainerId));
			return new CommonResponse<TrainerDto>(modelMapper.map(trainer, TrainerDto.class));
		} catch (CustomException e) {
			return new CommonResponse<TrainerDto>("01", e.getMessage());
		} catch (NumberFormatException e) {
			return new CommonResponse<TrainerDto>("06", "input must be a number");
		} catch (Exception e) {
			return new CommonResponse<TrainerDto>("06", e.getMessage());
		}
	} 
	
	@PostMapping(value="")
	public CommonResponse<TrainerDto> insert(@RequestBody TrainerDto trainerDto) throws CustomException {
		try {

			Trainer trainer =  modelMapper.map(trainerDto, Trainer.class);
			trainer.setTrainerId(0);
			trainer = trainerDao.save(trainer);
			
			return new CommonResponse<TrainerDto>(modelMapper.map(trainer, TrainerDto.class));
			
		} catch (CustomException e) {
			return new CommonResponse<TrainerDto>("14", "trainee not found");
		} catch (NumberFormatException e) {
			return new CommonResponse<TrainerDto>();
		} catch (Exception e) {
			return new CommonResponse<TrainerDto>();
		}
	}
	
	@PutMapping(value="")
	public CommonResponse<TrainerDto> update(@RequestBody TrainerDto trainerDto) throws CustomException {
		try {		
			Trainer checkTrainer =  trainerDao.getById(trainerDto.getTrainerId());
			if (checkTrainer == null) {
				return new CommonResponse<TrainerDto>("14", "trainer not found");
			}
			if (trainerDto.getFirstName() !=  null) {
				checkTrainer.setFirstName(trainerDto.getFirstName());
			}
			if (trainerDto.getLastName() != null) {
				checkTrainer.setLastName(trainerDto.getLastName());
			}
			if (trainerDto.getEmail() != null) {
				checkTrainer.setEmail(trainerDto.getEmail());
			}
			if (trainerDto.getPhone() != null) {
				checkTrainer.setPhone(trainerDto.getPhone());
			}
			if (trainerDto.isActiveFlag() != false) {
				checkTrainer.setActiveFlag(trainerDto.isActiveFlag());
			}
			
			checkTrainer = trainerDao.save(checkTrainer);
			
			return new CommonResponse<TrainerDto>(modelMapper.map(checkTrainer, TrainerDto.class));
			
		} catch (CustomException e) {
			return new CommonResponse("01", e.getMessage());
		} catch (Exception e) {
			return new CommonResponse("06", e.getMessage());
		}
	}
	
	@PutMapping(value="/{trainerId}")
	public CommonResponse<TrainerDto> delete(@PathVariable("trainerId") String trainerId) throws CustomException {
		try {
			Trainer checkTrainer = trainerDao.getById(Integer.parseInt(trainerId));
			if (checkTrainer !=  null) {
				checkTrainer.setActiveFlag(false);
			}
			checkTrainer = trainerDao.save(checkTrainer);
			return new CommonResponse<TrainerDto>(modelMapper.map(checkTrainer, TrainerDto.class));
			
		} catch (CustomException e) {
			return new CommonResponse("01", e.getMessage());
		} catch (Exception e) {
			return new CommonResponse("06", e.getMessage());
		}
	}
	
	@GetMapping(value="")
	public CommonResponse<List<TrainerDto>> getList(@RequestParam(name="list", defaultValue="") String trainerTemp) throws CustomException {
		
		try {
			List<Trainer> trainer = trainerDao.getList();
			
			return new CommonResponse<List<TrainerDto>>(trainer.stream()
					.map(trainer_temp -> modelMapper.map(trainer_temp, TrainerDto.class))
					.collect(Collectors.toList()));
			
		} catch (CustomException e) {
			throw e;
		} catch(NumberFormatException e) {
			return new CommonResponse("01", e.getMessage());
		} catch (Exception e) {
			return new CommonResponse("06", e.getMessage());
		}
	}
	
	@RequestMapping(value="/active")
	public CommonResponse<List<TrainerDto>> getListByActiveFlag(@RequestParam(name="list", defaultValue="") String trainerTemp) throws CustomException {
		
		try {
//			Trainer trainer = trainerDao.getByActiveFlag(Boolean.parseBoolean(trainerTemp));		
//			if (trainerTemp !=  null) {
//				trainer.setActiveFlag(trainer.getActiveFlag());
//			}

			List<Trainer> trainee = trainerDao.getListByActiveFlag();
			
			return new CommonResponse<List<TrainerDto>>(trainee.stream()
					.map(trainer_temp -> modelMapper.map(trainer_temp, TrainerDto.class))
					.collect(Collectors.toList()));
			
		} catch (CustomException e) {
			throw e;
		} catch(NumberFormatException e) {
			return new CommonResponse("01", e.getMessage());
		} catch (Exception e) {
			return new CommonResponse("06", e.getMessage());
		}
	}

}
